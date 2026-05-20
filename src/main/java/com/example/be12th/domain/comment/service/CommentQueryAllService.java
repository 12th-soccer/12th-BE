package com.example.be12th.domain.comment.service;

import com.example.be12th.domain.comment.domain.repository.CommentRepository;
import com.example.be12th.domain.comment.presentation.dto.response.CommentResponse;
import com.example.be12th.domain.join.domain.repository.JoinRepository;
import com.example.be12th.domain.notice.domain.Notice;
import com.example.be12th.domain.notice.domain.repository.NoticeRepository;
import com.example.be12th.domain.recruitment.domain.Recruitment;
import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentQueryAllService {
    private final CommentRepository commentRepository;
    private final JoinRepository joinRepository;
    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;
    private final UserFacade userFacade;

    private boolean canReadComments(User user, Recruitment recruitment) {
        if (recruitment.getUser().getId().equals(user.getId())) {
            return true;
        }
        return joinRepository.existsByUserAndRecruitment(user, recruitment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> execute(Long noticeId) {
        User user = userRepository.findById(userFacade.currentUserId())
                .orElseThrow(() -> new RuntimeException("해당 유저를 찾을수 없습니다."));

        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new RuntimeException("해당 공지방을 찾을수 없습니다."));

        if (!canReadComments(user, notice.getRecruitment())) {
            throw new RuntimeException("해당 모집방에 참여한 사람만 댓글을 조회할 수 있습니다.");
        }

        return commentRepository.findByNoticeOrderByCreatedAtAsc(notice).stream()
                .map(CommentResponse::from)
                .toList();
    }
}
