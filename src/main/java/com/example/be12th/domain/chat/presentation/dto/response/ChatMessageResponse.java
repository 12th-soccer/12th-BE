package com.example.be12th.domain.chat.presentation.dto.response;

import com.example.be12th.domain.chat.domain.ChatMessage;

import java.time.LocalDateTime;

public record ChatMessageResponse(
        Long id,
        Long matchId,
        Long senderId,
        String senderName,
        String content,
        LocalDateTime createdAt
) {
    public static ChatMessageResponse from(ChatMessage chatMessage) {
        return new ChatMessageResponse(
                chatMessage.getId(),
                chatMessage.getMatchId(),
                chatMessage.getUser().getId(),
                chatMessage.getUser().getName(),
                chatMessage.getContent(),
                chatMessage.getCreatedAt()
        );
    }
}
