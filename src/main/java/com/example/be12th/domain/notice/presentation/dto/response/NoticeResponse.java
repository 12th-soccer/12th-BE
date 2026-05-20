package com.example.be12th.domain.notice.presentation.dto.response;

import com.example.be12th.domain.notice.domain.Notice;

public record NoticeResponse(
        Long id,
        String title,
        String description,
        Integer headCount
) {
    public static NoticeResponse from(Notice notice) {
        return new NoticeResponse(
                notice.getId(),
                notice.getRecruitment().getTitle(),
                notice.getDescription(),
                notice.getRecruitment().getHeadCount()
        );
    }
}
