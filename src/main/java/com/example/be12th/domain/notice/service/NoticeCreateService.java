package com.example.be12th.domain.notice.service;

import com.example.be12th.domain.join.domain.repository.JoinRepository;
import com.example.be12th.domain.notice.domain.Notice;
import com.example.be12th.domain.notice.domain.repository.NoticeRepository;
import com.example.be12th.domain.notice.presentation.dto.request.NoticeRequest;
import com.example.be12th.domain.recruitment.domain.Recruitment;
import com.example.be12th.domain.recruitment.domain.repository.RecruitmentRepository;
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
public class NoticeCreateService {

    private static final int MIN_NOTICE_MEMBER_COUNT = 4;
    private final NoticeRepository noticeRepository;
    private final JoinRepository joinRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final UserRepository userRepository;
    private final UserFacade userFacade;

    private boolean canCreateNotice(User user, Recruitment recruitment) {
        return recruitment.getUser().getId().equals(user.getId());
    }

    @Transactional
    public void execute(Long recruitmentId, @Valid NoticeRequest noticeRequest) {
        User user = userRepository.findById(userFacade.currentUserId())
                .orElseThrow(() -> new RuntimeException("해당 유저를 찾을수 없습니다."));

        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new RuntimeException("해당 모집글을 찾을수 없습니다."));

        if (!canCreateNotice(user, recruitment)) {
            throw new RuntimeException("공지글은 모집글 작성자만 작성할 수 있습니다.");
        }

        long memberCount = joinRepository.countByRecruitment(recruitment) + 1;
        if (memberCount < MIN_NOTICE_MEMBER_COUNT) {
            throw new RuntimeException("공지글은 최소 4명이 모여야 작성할 수 있습니다.");
        }

        Notice notice = Notice.builder()
                .user(user)
                .recruitment(recruitment)
                .description(noticeRequest.description())
                .build();

        noticeRepository.save(notice);
    }
}
