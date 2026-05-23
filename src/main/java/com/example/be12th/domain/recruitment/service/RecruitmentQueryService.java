package com.example.be12th.domain.recruitment.service;

import com.example.be12th.domain.join.domain.repository.JoinRepository;
import com.example.be12th.domain.recruitment.domain.Recruitment;
import com.example.be12th.domain.recruitment.domain.repository.RecruitmentRepository;
import com.example.be12th.domain.recruitment.presentation.dto.response.RecruitmentQueryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecruitmentQueryService {
    private final RecruitmentRepository recruitmentRepository;
    private final JoinRepository joinRepository;

    @Transactional(readOnly = true)
    public RecruitmentQueryResponse execute(Long recruitmentId) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new RuntimeException("해당 모집 게시글을 찾을수 없습니다."));

        int currentParticipants = Math.toIntExact(joinRepository.countByRecruitment(recruitment) + 1);

        return RecruitmentQueryResponse.from(recruitment.getUser(), recruitment, currentParticipants);
    }
}
