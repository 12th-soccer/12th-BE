package com.example.be12th.domain.recruitment.presentation.dto.response;

import com.example.be12th.domain.notice.domain.Notice;
import com.example.be12th.domain.recruitment.domain.Recruitment;
import com.example.be12th.domain.recruitment.domain.category.AgeGroup;
import com.example.be12th.domain.recruitment.domain.category.GenderGroup;
import com.example.be12th.domain.recruitment.domain.category.K1Group;
import com.example.be12th.domain.recruitment.domain.category.K2Group;
import com.example.be12th.domain.user.domain.User;

import java.time.LocalDateTime;

public record RecruitmentQueryResponse(
        Long id,
        Long notice_id,
        Long hostId,
        String hostName,
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
    public static RecruitmentQueryResponse from(Notice notice, User user, Recruitment recruitment, Integer currentParticipants) {
        return new RecruitmentQueryResponse(
                recruitment.getRecruitmentId(),
                notice != null ? notice.getId() : null,
                user.getId(),
                user.getName(),
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
