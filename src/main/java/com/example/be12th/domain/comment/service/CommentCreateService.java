package com.example.be12th.domain.comment.service;

import com.example.be12th.domain.comment.domain.Comment;
import com.example.be12th.domain.comment.domain.repository.CommentRepository;
import com.example.be12th.domain.comment.presentation.dto.request.CommentRequest;
import com.example.be12th.domain.join.domain.repository.JoinRepository;
import com.example.be12th.domain.notice.domain.Notice;
import com.example.be12th.domain.notice.domain.repository.NoticeRepository;
import com.example.be12th.domain.recruitment.domain.Recruitment;
import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.facade.UserFacade;
import com.example.be12th.global.error.exception.App12thException;
import com.example.be12th.global.error.exception.ErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class CommentCreateService {
    private final CommentRepository commentRepository;
    private final JoinRepository joinRepository;
    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;
    private final UserFacade userFacade;

    private boolean canCreateComment(User user, Recruitment recruitment) {
        if (recruitment.getUser().getId().equals(user.getId())) {
            return true;
        }
        return joinRepository.existsByUserAndRecruitment(user, recruitment);
    }

    @Transactional
    public void execute(Long noticeId, @Valid CommentRequest commentRequest) {
        User user = userRepository.findById(userFacade.currentUserId())
                .orElseThrow(() -> new App12thException(ErrorCode.USER_NOT_FOUND));

        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new App12thException(ErrorCode.NOTICE_NOT_FOUND));

        if (!canCreateComment(user, notice.getRecruitment())) {
            throw new App12thException(ErrorCode.COMMENT_CREATE_FORBIDDEN);
        }

        Comment comment = Comment.builder()
                .user(user)
                .notice(notice)
                .content(commentRequest.content())
                .build();

        commentRepository.save(comment);
    }
}
