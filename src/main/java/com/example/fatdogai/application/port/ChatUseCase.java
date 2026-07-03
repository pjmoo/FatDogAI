package com.example.fatdogai.application.port;

import com.example.fatdogai.domain.model.Chat;
import java.util.List;

public interface ChatUseCase {
    List<Chat> findAllByUserId(String userId);
    void save(Chat chat);
}
