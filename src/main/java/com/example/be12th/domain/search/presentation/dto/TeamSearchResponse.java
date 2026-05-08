package com.example.be12th.domain.search.presentation.dto;

public record TeamSearchResponse(
        Long teamId,
        String teamName,
        String teamLogo
) {
}
