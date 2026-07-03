package com.example.fatdogai.application.service;

import com.example.fatdogai.application.port.ChatProvider;
import com.example.fatdogai.application.port.ChatRepository;
import com.example.fatdogai.application.port.ChatUseCase;
import com.example.fatdogai.domain.model.Chat;
import com.example.fatdogai.infrastructure.persistence.InMemoryChatRepository;
import com.example.fatdogai.infrastructure.external.GenAIChatProvider;
import com.example.fatdogai.infrastructure.external.NimChatProvider;

import java.time.ZonedDateTime;
import java.util.List;

public class GeminiChatService implements ChatUseCase {

    private final ChatRepository chatRepository;
    private final ChatProvider chatProvider;

    @Override
    public void save(Chat chat) {
        chatRepository.save(chat);
        List<Chat> history = chatRepository.findAllByUserId(chat.userId());
        ChatProvider activeProvider = (chat.model() != null && chat.model().toLowerCase().contains("nemotron"))
                ? NimChatProvider.getInstance()
                : chatProvider;
        String aiResponse = activeProvider.useAI(chat, history);
        Chat aiChat = new Chat(
                aiResponse,
                "AI",
                chat.userId(),
                chat.model(),
                ZonedDateTime.now().toString()
        );
        chatRepository.save(aiChat);
    }

    @Override
    public List<Chat> findAllByUserId(String userId) {
        return chatRepository.findAllByUserId(userId);
    }

    // 싱글톤 등록
    private GeminiChatService() {
        this.chatRepository = InMemoryChatRepository.getInstance();
        this.chatProvider = GenAIChatProvider.getInstance();
    }

    private static final GeminiChatService instance = new GeminiChatService();

    public static GeminiChatService getInstance() {
        return instance;
    }
}
