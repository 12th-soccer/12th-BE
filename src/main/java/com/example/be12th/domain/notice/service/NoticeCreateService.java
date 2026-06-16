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
                .orElseThrow(() -> new App12thException(ErrorCode.USER_NOT_FOUND));

        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new App12thException(ErrorCode.RECRUITMENT_NOT_FOUND));

        if (!canCreateNotice(user, recruitment)) {
            throw new App12thException(ErrorCode.NOTICE_CREATE_FORBIDDEN);
        }

        long memberCount = joinRepository.countByRecruitment(recruitment) + 1;
        if (memberCount < MIN_NOTICE_MEMBER_COUNT) {
            throw new App12thException(ErrorCode.NOTICE_MIN_MEMBER_REQUIRED);
        }

        Notice notice = Notice.builder()
                .user(user)
                .recruitment(recruitment)
                .description(noticeRequest.description())
                .build();

        noticeRepository.save(notice);
    }
}
