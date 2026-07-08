package com.example.be12th.global.websocket;

import com.example.be12th.global.error.exception.App12thException;
import com.example.be12th.global.error.exception.ErrorCode;
import com.example.be12th.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor == null || !StompCommand.CONNECT.equals(accessor.getCommand())) {
            return message;
        }

        String authorization = accessor.getFirstNativeHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new App12thException(ErrorCode.MISSING_WEBSOCKET_AUTHORIZATION);
        }

        String token = authorization.substring(7);
        jwtTokenProvider.validateToken(token);

        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        accessor.setUser(authentication);
        accessor.setLeaveMutable(true);

        return message;

    }
}
