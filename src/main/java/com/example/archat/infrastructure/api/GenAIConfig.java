package com.example.archat.infrastructure.api;

import com.google.genai.Client;
import com.google.genai.types.*;

public class GenAIConfig {

    public static Client getClient() {
        String apiKey = System.getenv("GEMINI_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            apiKey = System.getProperty("GEMINI_API_KEY");
        }
        return Client.builder()
                .apiKey(apiKey).build();
    }

    private static final String SYSTEM_INSTRUCTION = "친절한 말투로, 100자 이내로, 가능한 한글로 답변.";

    public static GenerateContentConfig getGenerateContentConfig() {
        return GenerateContentConfig
                .builder()
                .maxOutputTokens(512)
                .thinkingConfig(
                        ThinkingConfig.builder()
                                .includeThoughts(false)
                                .thinkingLevel(ThinkingLevel.Known.MINIMAL)
                                .build()
                )
                .systemInstruction(
                        Content.builder().parts(
                                Part.builder().text(SYSTEM_INSTRUCTION).build()).build())
                .build();
    }

}
