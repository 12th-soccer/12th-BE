package com.example.be12th.domain.recruitment.service;

import com.example.be12th.domain.recruitment.domain.Recruitment;
import com.example.be12th.domain.recruitment.domain.repository.RecruitmentRepository;
import com.example.be12th.domain.recruitment.presentation.dto.request.RecruitmentRequest;
import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.facade.UserFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Service
@Validated
@RequiredArgsConstructor
public class RecruitmentCreateService {
    private final RecruitmentRepository recruitmentRepository;
    private final UserRepository userRepository;
    private final UserFacade userFacade;

    @Transactional
    public void execute(@Valid RecruitmentRequest recruitmentRequest) {
        Long currentId = userFacade.currentUserId();

        User user = userRepository.findById(currentId)
                .orElseThrow(() -> new RuntimeException("해당 유저를 찾을수 없습니다."));

        if ((recruitmentRequest.k1Group() == null) == (recruitmentRequest.k2Group() == null)) {
            throw new IllegalArgumentException("k1Group 또는 k2Group 중 하나만 선택해야 합니다.");
        }

        Recruitment recruitment = Recruitment.builder()
                .title(recruitmentRequest.title())
                .content(recruitmentRequest.content())
                .headCount(recruitmentRequest.headCount())
                .ageGroup(recruitmentRequest.ageGroup())
                .genderGroup(recruitmentRequest.genderGroup())
                .k1Group(recruitmentRequest.k1Group())
                .k2Group(recruitmentRequest.k2Group())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        recruitmentRepository.save(recruitment);
    }
}
