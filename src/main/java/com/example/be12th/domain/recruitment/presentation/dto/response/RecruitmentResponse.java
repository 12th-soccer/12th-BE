package com.example.be12th.domain.recruitment.presentation.dto.response;

import com.example.be12th.domain.recruitment.domain.Recruitment;
import com.example.be12th.domain.recruitment.domain.category.AgeGroup;
import com.example.be12th.domain.recruitment.domain.category.GenderGroup;
import com.example.be12th.domain.recruitment.domain.category.K1Group;
import com.example.be12th.domain.recruitment.domain.category.K2Group;

public record RecruitmentResponse(
        String title,
        String content,
        AgeGroup ageGroup,
        GenderGroup genderGroup,
        K1Group k1Group,
        K2Group k2Group
) {
    public static RecruitmentResponse from(Recruitment recruitment) {
        return new RecruitmentResponse(
                recruitment.getTitle(),
                recruitment.getContent(),
                recruitment.getAgeGroup(),
                recruitment.getGenderGroup(),
                recruitment.getK1Group(),
                recruitment.getK2Group()
        );
    }
}