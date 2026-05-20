package com.example.be12th.domain.comment.domain.repository;

import com.example.be12th.domain.comment.domain.Comment;
import com.example.be12th.domain.notice.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByNoticeOrderByCreatedAtAsc(Notice notice);
}
