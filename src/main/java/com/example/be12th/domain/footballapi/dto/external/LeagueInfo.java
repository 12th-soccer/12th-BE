package com.example.be12th.domain.footballapi.dto.external;

public record LeagueInfo(
        Long id,
        String name,
        String country,
        String logo,
        String flag,
        Integer season,
        String round,
        Boolean standings
) {
}
