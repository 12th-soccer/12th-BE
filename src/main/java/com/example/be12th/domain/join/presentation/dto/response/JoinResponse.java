package com.example.be12th.domain.join.presentation.dto.response;

import com.example.be12th.domain.join.domain.Join;
import com.example.be12th.domain.recruitment.domain.Recruitment;
import com.example.be12th.domain.recruitment.domain.category.AgeGroup;
import com.example.be12th.domain.recruitment.domain.category.GenderGroup;
import com.example.be12th.domain.recruitment.domain.category.K1Group;
import com.example.be12th.domain.recruitment.domain.category.K2Group;

import java.time.LocalDateTime;

public record JoinResponse(
        Long joinId,
        Long recruitmentId,
        String title,
        String content,
        LocalDateTime createdDate,
        LocalDateTime expiredAt,
        Integer headCount,
        Integer currentParticipants,
        AgeGroup ageGroup,
        GenderGroup genderGroup,
        K1Group k1Group,
        K2Group k2Group
) {
    public static JoinResponse from(Join join, Integer currentParticipants) {
        Recruitment recruitment = join.getRecruitment();

        return new JoinResponse(
                join.getId(),
                recruitment.getRecruitmentId(),
                recruitment.getTitle(),
                recruitment.getContent(),
                recruitment.getCreatedAt(),
                recruitment.getExpiredAt(),
                recruitment.getHeadCount(),
                currentParticipants,
                recruitment.getAgeGroup(),
                recruitment.getGenderGroup(),
                recruitment.getK1Group(),
                recruitment.getK2Group()
        );
    }
}
