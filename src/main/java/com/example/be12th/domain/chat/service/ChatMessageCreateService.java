package com.example.be12th.domain.chat.service;

import com.example.be12th.domain.chat.domain.ChatMessage;
import com.example.be12th.domain.chat.domain.repository.ChatMessageRepository;
import com.example.be12th.domain.chat.presentation.dto.request.ChatMessageRequest;
import com.example.be12th.domain.chat.presentation.dto.response.ChatMessageResponse;
import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.facade.UserFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class ChatMessageCreateService {
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final UserFacade userFacade;

    @Transactional
    public ChatMessageResponse execute(Long matchId, @Valid ChatMessageRequest request) {
        User user = userRepository.findById(userFacade.currentUserId())
                .orElseThrow(() -> new RuntimeException("해당 유저를 찾을수 없습니다."));

        ChatMessage chatMessage = ChatMessage.builder()
                .matchId(matchId)
                .user(user)
                .content(request.content())
                .build();

        return ChatMessageResponse.from(chatMessageRepository.save(chatMessage));
    }
}
