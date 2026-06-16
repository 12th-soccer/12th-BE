package com.example.be12th.domain.recruitment.service;

import com.example.be12th.domain.join.domain.repository.JoinRepository;
import com.example.be12th.domain.notice.domain.Notice;
import com.example.be12th.domain.notice.domain.repository.NoticeRepository;
import com.example.be12th.domain.recruitment.domain.Recruitment;
import com.example.be12th.domain.recruitment.domain.repository.RecruitmentRepository;
import com.example.be12th.domain.recruitment.presentation.dto.response.RecruitmentQueryResponse;
import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.facade.UserFacade;
import com.example.be12th.global.error.exception.App12thException;
import com.example.be12th.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecruitmentQueryService {
    private final RecruitmentRepository recruitmentRepository;
    private final JoinRepository joinRepository;
    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;
    private final UserFacade userFacade;

    @Transactional(readOnly = true)
    public RecruitmentQueryResponse execute(Long recruitmentId) {

        Long currentId = userFacade.currentUserId();

        User user = userRepository.findById(currentId)
                .orElseThrow(() -> new App12thException(ErrorCode.USER_NOT_FOUND));

        if (!user.isPhoneVerified()) {
            throw new App12thException(ErrorCode.PHONE_VERIFICATION_REQUIRED);
        }

        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new App12thException(ErrorCode.RECRUITMENT_NOT_FOUND));

        Notice notice = noticeRepository.findByRecruitment(recruitment)
                .orElse(null);

        int currentParticipants = Math.toIntExact(joinRepository.countByRecruitment(recruitment) + 1);

        return RecruitmentQueryResponse.from(notice ,recruitment.getUser(), recruitment, currentParticipants);
    }
}
