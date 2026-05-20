package com.example.be12th.domain.comment.presentation.dto.response;

import com.example.be12th.domain.comment.domain.Comment;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        String username,
        String content,
        LocalDateTime createdAt
) {
    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getUser().getName(),
                comment.getContent(),
                comment.getCreatedAt()
        );
    }
}
