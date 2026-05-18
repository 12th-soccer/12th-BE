package com.example.be12th.domain.recruitment.service;

import com.example.be12th.domain.recruitment.domain.Recruitment;
import com.example.be12th.domain.recruitment.domain.repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecruitmentDeleteService {
    private final RecruitmentRepository recruitmentRepository;

    @Transactional
    public void execute(Long recruitmentId) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new RuntimeException("해당 게시물을 찾을수 없습니다."));

        recruitmentRepository.delete(recruitment);
    }
}
