package com.example.fatdogai.infrastructure.external;

public final class GroqAIConfig {

    private GroqAIConfig() {
    }

    public static String getApiUrl() {
        return "https://api.groq.com/openai/v1/chat/completions";
    }

    public static String getApiKey() {
        String apiKey = System.getenv("GROQ_API_KEY");
        if (apiKey == null || apiKey.isBlank()) {
            apiKey = System.getProperty("GROQ_API_KEY");
        }
        return apiKey;
    }

    public static String getDefaultModel() {
        return "openai/gpt-oss-20b";
    }

    public static String getSystemInstruction() {
        return " 라는 질문에 한국어로만 답변해라. 최종 답변만 한 줄로 출력해라. "
                + "생각 과정, 분석, 단계 설명, 메타설명은 절대 출력하지 마라. "
                + "항상 100자 이내로, 간결하게 답변해라.";
    }
}
