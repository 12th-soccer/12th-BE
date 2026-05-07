package com.example.be12th.domain.team.presentation.dto.response;

public record TeamResponse(
        Long teamId,
        String name,
        String country,
        Integer founded,
        String logo,
        Long venueId,
        String venueName,
        String venueAddress,
        String venueCity,
        Integer venueCapacity
) {
}
