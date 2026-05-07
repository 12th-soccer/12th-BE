package com.example.be12th.domain.ranking.presentation.dto;

import java.util.List;

public record StandingLeagueInfo(
        Long id,
        String name,
        String country,
        String logo,
        String flag,
        Integer season,
        List<List<StandingItem>> standings
) {
}
