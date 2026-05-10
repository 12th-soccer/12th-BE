package com.example.be12th.domain.recruitment.service;

import com.example.be12th.domain.recruitment.domain.Recruitment;
import com.example.be12th.domain.recruitment.domain.repository.RecruitmentRepository;
import com.example.be12th.domain.recruitment.presentation.dto.response.RecruitmentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecruitmentQueryService {
    private final RecruitmentRepository recruitmentRepository;

    @Transactional(readOnly = true)
    public RecruitmentResponse execute(Long recruitmentId) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new RuntimeException("해당 모집 게시글을 찾을수 없습니다."));

        return RecruitmentResponse.from(recruitment);
    }
}
