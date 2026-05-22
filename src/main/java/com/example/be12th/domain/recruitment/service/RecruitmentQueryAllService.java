package com.example.be12th.domain.recruitment.service;

import com.example.be12th.domain.join.domain.repository.JoinRepository;
import com.example.be12th.domain.recruitment.domain.repository.RecruitmentRepository;
import com.example.be12th.domain.recruitment.presentation.dto.response.RecruitmentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecruitmentQueryAllService {
    private final RecruitmentRepository recruitmentRepository;
    private final JoinRepository joinRepository;

    @Transactional(readOnly = true)
    public Page<RecruitmentResponse> execute(Pageable pageable) {
        return recruitmentRepository.findAll(pageable)
                .map(recruitment -> RecruitmentResponse.from(
                        recruitment,
                        Math.toIntExact(joinRepository.countByRecruitment(recruitment) + 1)
                ));
    }
}
