package com.example.be12th.domain.chat.presentation;

import com.example.be12th.domain.chat.presentation.dto.request.ChatMessageRequest;
import com.example.be12th.domain.chat.presentation.dto.response.ChatMessageResponse;
import com.example.be12th.domain.chat.service.ChatMessageCreateService;
import com.example.be12th.domain.chat.service.ChatMessagePublisher;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final ChatMessageCreateService chatMessageCreateService;
    private final ChatMessagePublisher chatMessagePublisher;

    @MessageMapping("/chat/{matchId}")
    public void sendMessage(
            @DestinationVariable Long matchId,
            @Valid @Payload ChatMessageRequest request,
            Principal principal
    ) {

        ChatMessageResponse response = chatMessageCreateService.execute(matchId, request, principal.getName());
        chatMessagePublisher.publish(matchId, response);
    }
}
