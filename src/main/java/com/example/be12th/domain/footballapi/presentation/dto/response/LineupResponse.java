package com.example.be12th.domain.footballapi.presentation.dto.response;

public record LineupResponse(
        Long teamId,
        String teamName,
        String formation,
        String coachName
) {
}
