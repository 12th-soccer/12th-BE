package com.example.be12th.domain.footballapi.presentation.dto.response;

public record PlayerResponse(
        Long playerId,
        String name,
        Integer age,
        String nationality,
        String photo,
        String position,
        Long teamId,
        String teamName,
        String teamLogo,
        Integer number
) {
}