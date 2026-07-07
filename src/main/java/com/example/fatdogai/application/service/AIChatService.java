package com.example.fatdogai.application.service;

import com.example.fatdogai.application.port.ChatProvider;
import com.example.fatdogai.application.port.ChatRepository;
import com.example.fatdogai.application.port.ChatUseCase;
import com.example.fatdogai.domain.model.Chat;
import com.example.fatdogai.infrastructure.external.NimChatProvider;
import com.example.fatdogai.infrastructure.persistence.InMemoryChatRepository;
import com.example.fatdogai.infrastructure.external.GenAIChatProvider;
import com.example.fatdogai.infrastructure.external.GroqChatProvider;

import java.time.ZonedDateTime;
import java.util.List;

public class AIChatService implements ChatUseCase {

    private final ChatRepository chatRepository;
    private final ChatProvider groqChatProvider;
    private final ChatProvider genAIChatProvider;

    @Override
    public void save(Chat chat) {
        chatRepository.save(chat);
        List<Chat> history = chatRepository.findAllByUserId(chat.userId());
        String aiResponse = "";
        if (chat.model() != null && (chat.model().contains("gemini") || chat.model().contains("gemma"))) {
            aiResponse = genAIChatProvider.useAI(chat, history);
        } else if(chat.model().toLowerCase().contains("nemotron")){
          //  aiResponse = NimChatProvider.useAI(chat, history);
        } else {
            aiResponse = groqChatProvider.useAI(chat, history);
        }
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
    private AIChatService() {
        this.chatRepository = InMemoryChatRepository.getInstance();
        this.genAIChatProvider = GenAIChatProvider.getInstance();
        this.groqChatProvider = GroqChatProvider.getInstance();
    }

    private static final AIChatService instance = new AIChatService();

    public static AIChatService getInstance() {
        return instance;
    }
}
