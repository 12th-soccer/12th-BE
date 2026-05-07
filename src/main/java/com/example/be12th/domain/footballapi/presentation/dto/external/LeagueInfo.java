package com.example.be12th.domain.footballapi.presentation.dto.external;

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