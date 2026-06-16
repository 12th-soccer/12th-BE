package com.example.be12th.domain.chat.presentation;

import com.example.be12th.domain.chat.presentation.dto.request.ChatMessageRequest;
import com.example.be12th.domain.chat.presentation.dto.response.ChatMessageResponse;
import com.example.be12th.domain.chat.service.ChatMessageCreateService;
import com.example.be12th.domain.chat.service.ChatMessagePublisher;
import com.example.be12th.global.error.exception.App12thException;
import com.example.be12th.global.error.exception.ErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
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
        if (principal == null) {
            throw new App12thException(ErrorCode.AUTHENTICATION_REQUIRED);
        }

        ChatMessageResponse response = chatMessageCreateService.execute(matchId, request, principal.getName());
        chatMessagePublisher.publish(matchId, response);
    }
}
