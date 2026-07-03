package com.example.fatdogai.infrastructure.persistence;

import com.example.fatdogai.domain.model.Chat;
import com.example.fatdogai.application.port.ChatRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryChatRepository implements ChatRepository {
    private InMemoryChatRepository() {
    }

    private static final InMemoryChatRepository instance = new InMemoryChatRepository();

    public static InMemoryChatRepository getInstance() {
        return instance;
    }

    private final ConcurrentHashMap<String, List<Chat>> chatMap = new ConcurrentHashMap<>();

    @Override
    public void save(Chat chat) {
        chatMap.computeIfAbsent(
                chat.userId(),
                k -> new ArrayList<>()
        ).add(chat);
    }

    @Override
    public List<Chat> findAllByUserId(String userId) {
        return chatMap.getOrDefault(userId, Collections.emptyList());
    }
}
