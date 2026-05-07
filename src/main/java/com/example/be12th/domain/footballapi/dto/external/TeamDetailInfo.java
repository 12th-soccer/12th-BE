package com.example.be12th.domain.footballapi.dto.external;

public record TeamDetailInfo(
        Long id,
        String name,
        String code,
        String country,
        Integer founded,
        String logo
) {
}
