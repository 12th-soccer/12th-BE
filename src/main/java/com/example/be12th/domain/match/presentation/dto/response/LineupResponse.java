package com.example.be12th.domain.match.presentation.dto.response;

public record LineupResponse(
        Long teamId,
        String teamName,
        String formation,
        String coachName
) {
}
