package com.example.fatdogai.infrastructure.external;

import com.example.fatdogai.application.port.ChatProvider;
import com.example.fatdogai.domain.model.Chat;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class GroqChatProvider implements ChatProvider {
    private final Gson gson = new Gson();

    @Override
    public String useAI(Chat chat) {
        return useAI(chat, List.of(chat));
    }

    @Override
    public String useAI(Chat newChat, List<Chat> chatHistory) {
        String apiKey = GroqAIConfig.getApiKey();
        if (apiKey == null || apiKey.isBlank()) {
            return "GROQ_API_KEY가 설정되지 않았습니다. .env 파일을 확인해 주세요.";
        }

        String model = newChat.model();
        if (model == null || model.isBlank()) {
            model = GroqAIConfig.getDefaultModel();
        }

        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("model", model);

        JsonArray messages = new JsonArray();
        JsonObject systemMessage = new JsonObject();
        systemMessage.addProperty("role", "system");
        systemMessage.addProperty("content", GroqAIConfig.getSystemInstruction());
        messages.add(systemMessage);

        for (Chat chat : chatHistory) {
            JsonObject message = new JsonObject();
            message.addProperty("role", "USER".equalsIgnoreCase(chat.owner()) ? "user" : "assistant");
            message.addProperty("content", chat.message() + GroqAIConfig.getSystemInstruction());
            messages.add(message);
        }

        requestBody.add("messages", messages);
        requestBody.addProperty("temperature", 0.7);
        requestBody.addProperty("max_tokens", 512);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GroqAIConfig.getApiUrl()))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(requestBody)))
                .build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                return "GROQ API 호출 실패 (상태 코드: %s): %s".formatted(response.statusCode(), response.body());
            }

            JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);
            if (jsonResponse == null || !jsonResponse.has("choices")) {
                return "GROQ API 응답을 해석할 수 없습니다.";
            }

            JsonArray choices = jsonResponse.getAsJsonArray("choices");
            if (choices.isEmpty()) {
                return "GROQ API 응답에 선택지가 없습니다.";
            }

            JsonObject choice = choices.get(0).getAsJsonObject();
            if (!choice.has("message")) {
                return "GROQ API 응답에 메시지가 없습니다.";
            }

            JsonObject message = choice.getAsJsonObject("message");
            String data = getFinalAnswer(message.get("content").getAsString());
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return "GROQ API 통신 중 오류 발생: %s".formatted(e.getMessage());
        }
    }

    private GroqChatProvider() {
    }

    private static final GroqChatProvider instance = new GroqChatProvider();

    public static GroqChatProvider getInstance() {
        return instance;
    }

    public static String getFinalAnswer(String rawText) {
        if (rawText == null || rawText.trim().isEmpty()) {
            return "답변을 가져오지 못했습니다.";
        }

        // 1. 줄바꿈을 기준으로 전체 텍스트를 쪼갭니다.
        String[] lines = rawText.split("\\r?\\n");

        // 뒤에서부터 역순으로 탐색하면서 '진짜 한글 답변'을 찾습니다.
        for (int i = lines.length - 1; i >= 0; i--) {
            String line = lines[i].trim();

            // 빈 줄은 건너跳니다.
            if (line.isEmpty()) continue;

            // 2. 만약 화살표(->)가 있다면 화살표 오른쪽의 진짜 답변만 취합니다.
            if (line.contains("->")) {
                String[] parts = line.split("->");
                line = parts[parts.length - 1].trim();
            }

            // 3. 문장에서 영어 단어나 기술적 마커(Output:, Refined: 등)를 완전히 제거합니다.
            // [a-zA-Z]와 마커에 자주 쓰이는 특수문자(*, :)를 제거합니다.
            String cleanLine = line.replaceAll("(?i)[a-z\\*\\:]", "").trim();

            // 4. 영어를 지우고 남은 문장에 '한글'이 포함되어 있다면 그것을 최종 답변으로 확정합니다.
            // (정규식 \\p{IsHangul} 사용)
            if (cleanLine.matches(".*\\p{IsHangul}.*")) {
                return cleanLine;
            }
        }

        return "답변을 가져오지 못했습니다.";
    }
}
