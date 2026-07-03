package com.example.fatdogai.infrastructure.external;

import com.example.fatdogai.application.port.ChatProvider;
import com.example.fatdogai.domain.model.Chat;

import java.util.List;

public class GroqChatProvider implements ChatProvider {

    @Override
    public String useAI(Chat chat) {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public String useAI(Chat newChat, List<Chat> chatHistory) {
        throw new RuntimeException("Not Implemented");
    }

    private GroqChatProvider() {
    }

    private static final GroqChatProvider instance = new GroqChatProvider();

    public static GroqChatProvider getInstance() {
        return instance;
    }
}
