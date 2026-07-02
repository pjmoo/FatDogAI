package com.example.archat.infrastructure.api;

import com.example.archat.application.port.ChatProvider;
import com.example.archat.domain.model.Chat;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class NimChatProvider implements ChatProvider {
    private static final String NIM_API_URL = "https://integrate.api.nvidia.com/v1/chat/completions";
    private final Gson gson = new Gson();

    @Override
    public String useAI(Chat chat) {
        return useAI(chat, List.of(chat));
    }

    @Override
    public String useAI(Chat newChat, List<Chat> chatHistory) {
        String nimApiKey = System.getenv("NIM_API_KEY");
        if (nimApiKey == null || nimApiKey.isEmpty()) {
            nimApiKey = System.getProperty("NIM_API_KEY");
        }
        
        if (nimApiKey == null || nimApiKey.isEmpty()) {
            return "NIM_API_KEY가 설정되지 않았습니다. .env 파일을 확인해 주세요.";
        }

        try {
            JsonObject requestBody = new JsonObject();
            
            String model = newChat.model();
            if (model == null || model.isEmpty()) {
                model = "nvidia/nemotron-3-ultra-550b-a55b";
            } else if (!model.contains("/")) {
                model = "nvidia/" + model;
            }
            requestBody.addProperty("model", model);

            JsonArray messages = new JsonArray();
            for (Chat c : chatHistory) {
                JsonObject msg = new JsonObject();
                msg.addProperty("role", c.owner().equalsIgnoreCase("USER") ? "user" : "assistant");
                msg.addProperty("content", c.message());
                messages.add(msg);
            }
            requestBody.add("messages", messages);
            requestBody.addProperty("temperature", 0.5);
            requestBody.addProperty("max_tokens", 1024);

            String jsonPayload = gson.toJson(requestBody);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(NIM_API_URL))
                    .header("Authorization", "Bearer " + nimApiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);
                return jsonResponse.getAsJsonArray("choices")
                        .get(0).getAsJsonObject()
                        .getAsJsonObject("message")
                        .getAsJsonPrimitive("content")
                        .getAsString();
            } else {
                return "NIM API 호출 실패 (상태 코드: " + response.statusCode() + "): " + response.body();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "NIM API 통신 중 오류 발생: " + e.getMessage();
        }
    }

    private NimChatProvider() {}

    private static final NimChatProvider instance = new NimChatProvider();

    public static NimChatProvider getInstance() {
        return instance;
    }
}
