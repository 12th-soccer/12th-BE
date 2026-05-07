package com.example.be12th.domain.goal.presentation.dto;

public record PlayerGoalResponse(
        Long matchId,
        Long playerId,
        String playerName,
        String matchDate,
        Integer goalTime,
        String goalType
) {
}
