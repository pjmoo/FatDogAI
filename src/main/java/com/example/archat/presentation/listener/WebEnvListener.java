package com.example.archat.presentation.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebListener
public class WebEnvListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        List<String> logs = new ArrayList<>();
        logs.add("=== Env Loading Status (" + LocalDateTime.now() + ") ===");
        
        try {
            // Path 1: Servlet context real path
            String realPath = sce.getServletContext().getRealPath("/");
            logs.add("ServletContext Real Path: " + realPath);
            
            // Path 2: Classpath resources
            String classpathRes = null;
            try {
                var resource = WebEnvListener.class.getResource("/");
                if (resource != null) {
                    classpathRes = resource.getPath();
                }
            } catch (Exception e) {
                logs.add("Failed to get class resource path: " + e.getMessage());
            }
            logs.add("Classpath Resource Path: " + classpathRes);
            
            // Path 3: User dir
            String userDir = System.getProperty("user.dir");
            logs.add("System user.dir: " + userDir);

            // Collect all potential base directories to search .env in
            List<Path> basePaths = new ArrayList<>();
            if (realPath != null) basePaths.add(Paths.get(realPath));
            if (classpathRes != null) {
                // Remove leading slash on Windows if present (e.g. /C:/workspace/...)
                if (classpathRes.startsWith("/") && classpathRes.contains(":")) {
                    classpathRes = classpathRes.substring(1);
                }
                basePaths.add(Paths.get(classpathRes));
            }
            if (userDir != null) basePaths.add(Paths.get(userDir));
            basePaths.add(Paths.get("."));

            Path envPath = null;
            
            // Walk up parent directories for each base path to find .env
            for (Path base : basePaths) {
                Path temp = base.toAbsolutePath().normalize();
                logs.add("Searching upward from: " + temp);
                for (int i = 0; i < 5; i++) {
                    if (temp == null) break;
                    Path possibleEnv = temp.resolve(".env");
                    logs.add("  Checking: " + possibleEnv);
                    if (Files.exists(possibleEnv)) {
                        envPath = possibleEnv;
                        break;
                    }
                    temp = temp.getParent();
                }
                if (envPath != null) break;
            }

            if (envPath != null) {
                envPath = envPath.toAbsolutePath().normalize();
                logs.add("SUCCESS: Found .env file at: " + envPath);
                try (BufferedReader reader = Files.newBufferedReader(envPath)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        line = line.trim();
                        if (line.isEmpty() || line.startsWith("#")) {
                            continue;
                        }
                        int eqIdx = line.indexOf('=');
                        if (eqIdx > 0) {
                            String key = line.substring(0, eqIdx).trim();
                            String value = line.substring(eqIdx + 1).trim();
                            if (value.startsWith("\"") && value.endsWith("\"")) {
                                value = value.substring(1, value.length() - 1);
                            } else if (value.startsWith("'") && value.endsWith("'")) {
                                value = value.substring(1, value.length() - 1);
                            }
                            System.setProperty(key, value);
                            logs.add("  Loaded key: " + key + " (length: " + value.length() + ")");
                        }
                    }
                }
            } else {
                logs.add("ERROR: .env file NOT FOUND in any of the search paths.");
            }
        } catch (Exception e) {
            logs.add("ERROR: Exception during env load: " + e.getMessage());
            for (StackTraceElement ste : e.getStackTrace()) {
                logs.add("  at " + ste.toString());
            }
        } finally {
            // Write log to C:\workspace\FatDogAI\env_status.log for debugging
            try {
                Path logFile = Paths.get("C:\\workspace\\FatDogAI\\env_status.log");
                Files.write(logFile, logs, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
