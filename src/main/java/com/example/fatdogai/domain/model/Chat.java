package com.example.fatdogai.domain.model;

public record Chat(
        String message,
        String owner,
        String userId,
        String model,
        String timestamp
) {
}
