package com.example.be12th.domain.ranking.presentation.dto;

public record StandingAllStats(
        Integer played,
        Integer win,
        Integer draw,
        Integer lose,
        StandingGoalsStats goals
) {
}
