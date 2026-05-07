package com.example.be12th.domain.goal.presentation.dto;

public record PlayerGoalResponse(
        Long playerId,
        String playerName,
        Integer season,
        String league,
        Integer goalCount
) {
}
