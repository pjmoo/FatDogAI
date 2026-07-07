package com.example.fatdogai.presentation.controller;

import com.example.fatdogai.application.service.AIChatService;
import com.example.fatdogai.application.service.GeminiChatService;
import com.example.fatdogai.domain.model.Chat;
import com.example.fatdogai.application.port.ChatUseCase;
import com.example.fatdogai.presentation.dto.ChatResponseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;

@WebServlet("/chat")
public class ChatController extends BaseController {
    private ChatUseCase chatService;

    @Override
    public void init() throws ServletException {
        // chatService = GeminiChatService.getInstance();
        chatService = AIChatService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        List<ChatResponseDTO> response = chatService.findAllByUserId(session.getId())
                .stream()
                .map(ChatResponseDTO::of)
                .toList();

        req.setAttribute("chats", response);

        req.getRequestDispatcher("%s/%s".formatted(VIEW_PREFIX, "chat.jsp"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Chat chat = new Chat(
                req.getParameter("message"),
                "USER",
                req.getSession().getId(),
                req.getParameter("model"),
                ZonedDateTime.now().toString()
        );
        chatService.save(chat);
        req.getSession().setAttribute("selectedModel", req.getParameter("model"));
        resp.sendRedirect("%s/%s".formatted(req.getContextPath(), "chat"));
    }
}
