package com.example.be12th.domain.chat.domain.repository;

import com.example.be12th.domain.chat.domain.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    @Query("SELECT cm FROM ChatMessage cm JOIN FETCH cm.user WHERE cm.matchId = :matchId ORDER BY cm.createdAt ASC, cm.id ASC")
    Slice<ChatMessage> findByMatchIdWithUserOrderByCreatedAtAsc(@Param("matchId") Long matchId, Pageable pageable);
}
