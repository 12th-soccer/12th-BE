package com.example.be12th.domain.chat.service;

import com.example.be12th.domain.chat.presentation.dto.response.ChatMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatMessagePublisher {
    private final SimpMessagingTemplate messagingTemplate;

    public void publish(Long matchId, ChatMessageResponse response) {
        messagingTemplate.convertAndSend("/pub/chat/" + matchId, response);
    }
}
