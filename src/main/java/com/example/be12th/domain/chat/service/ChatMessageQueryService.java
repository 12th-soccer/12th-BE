package com.example.be12th.domain.chat.service;

import com.example.be12th.domain.chat.domain.repository.ChatMessageRepository;
import com.example.be12th.domain.chat.presentation.dto.response.ChatMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageQueryService {
    private final ChatMessageRepository chatMessageRepository;

    @Transactional(readOnly = true)
    public List<ChatMessageResponse> execute(Long matchId) {
        return chatMessageRepository.findByMatchIdOrderByCreatedAtAsc(matchId)
                .stream()
                .map(ChatMessageResponse::from)
                .toList();
    }
}
