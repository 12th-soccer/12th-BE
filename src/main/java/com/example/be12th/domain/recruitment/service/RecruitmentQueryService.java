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
                .orElseThrow(() -> new RuntimeException("해당 유저를 찾을수 없습니다."));

        if (!user.isPhoneVerified()) {
            throw new IllegalArgumentException("휴대전화번호 인증이 필요합니다.");
        }

        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new RuntimeException("해당 모집 게시글을 찾을수 없습니다."));

        Notice notice = noticeRepository.findByRecruitment(recruitment)
                .orElse(null);

        int currentParticipants = Math.toIntExact(joinRepository.countByRecruitment(recruitment) + 1);

        return RecruitmentQueryResponse.from(notice ,recruitment.getUser(), recruitment, currentParticipants);
    }
}
