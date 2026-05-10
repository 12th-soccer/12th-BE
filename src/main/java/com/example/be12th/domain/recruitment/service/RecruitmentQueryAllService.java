package com.example.be12th.domain.recruitment.service;

import com.example.be12th.domain.recruitment.domain.repository.RecruitmentRepository;
import com.example.be12th.domain.recruitment.presentation.dto.response.RecruitmentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecruitmentQueryAllService {
    private final RecruitmentRepository recruitmentRepository;

    public Page<RecruitmentResponse> execute(Pageable pageable) {
        return recruitmentRepository.findAll(pageable)
                .map(RecruitmentResponse::from);
    }
}
