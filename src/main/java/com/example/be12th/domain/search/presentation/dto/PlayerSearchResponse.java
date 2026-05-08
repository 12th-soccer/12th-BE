package com.example.be12th.domain.search.presentation.dto;

public record PlayerSearchResponse(
        Long playerId,
        String playerName,
        String playerPhoto
) {
}
