package com.example.fatdogai.application.port;

import com.example.fatdogai.domain.model.Chat;
import java.util.List;

public interface ChatProvider {
    String useAI(Chat chat);
    String useAI(Chat newChat, List<Chat> chatHistory);
}
