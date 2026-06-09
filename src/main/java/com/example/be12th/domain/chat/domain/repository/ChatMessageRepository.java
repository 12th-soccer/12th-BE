package com.example.be12th.domain.chat.domain.repository;

import com.example.be12th.domain.chat.domain.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByMatchIdOrderByCreatedAtAsc(Long matchId, Pageable pageable);
}
