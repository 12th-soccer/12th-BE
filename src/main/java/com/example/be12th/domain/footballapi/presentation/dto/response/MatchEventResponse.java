package com.example.be12th.domain.footballapi.presentation.dto.response;

public record MatchEventResponse(
        String time,
        String type,
        String detail,
        Long teamId,
        String teamName,
        Long playerId,
        String playerName,
        Long assistPlayerId,
        String assistPlayerName
) {
}