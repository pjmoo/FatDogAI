package com.example.fatdogai.application.port;

import com.example.fatdogai.domain.model.Chat;
import java.util.List;

public interface ChatRepository {
    void save(Chat chat);
    List<Chat> findAllByUserId(String userId);
}
