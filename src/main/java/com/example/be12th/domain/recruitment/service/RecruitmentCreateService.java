package com.example.be12th.domain.recruitment.service;

import com.example.be12th.domain.recruitment.domain.Recruitment;
import com.example.be12th.domain.recruitment.domain.repository.RecruitmentRepository;
import com.example.be12th.domain.recruitment.presentation.dto.request.RecruitmentRequest;
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
public class RecruitmentCreateService {
    private final RecruitmentRepository recruitmentRepository;
    private final UserRepository userRepository;
    private final UserFacade userFacade;

    @Transactional
    public void execute(@Valid RecruitmentRequest recruitmentRequest) {
        Long currentId = userFacade.currentUserId();

        User user = userRepository.findById(currentId)
                .orElseThrow(() -> new App12thException(ErrorCode.USER_NOT_FOUND));

        if ((recruitmentRequest.k1Group() == null) == (recruitmentRequest.k2Group() == null)) {
            throw new App12thException(ErrorCode.INVALID_RECRUITMENT_LEAGUE_GROUP);
        }

        if (!user.isPhoneVerified()) {
            throw new App12thException(ErrorCode.PHONE_VERIFICATION_REQUIRED);
        }

        Recruitment recruitment = Recruitment.builder()
                .title(recruitmentRequest.title())
                .content(recruitmentRequest.content())
                .headCount(recruitmentRequest.headCount())
                .expiredAt(recruitmentRequest.expiredAt())
                .ageGroup(recruitmentRequest.ageGroup())
                .genderGroup(recruitmentRequest.genderGroup())
                .k1Group(recruitmentRequest.k1Group())
                .k2Group(recruitmentRequest.k2Group())
                .user(user)
                .build();

        recruitmentRepository.save(recruitment);
    }
}
