package com.example.be12th.domain.team.presentation.dto.response;

import com.example.be12th.domain.match.presentation.dto.response.MatchResponse;

import java.util.List;

public record TeamDetailResponse(
        Long teamId,
        String name,
        String country,
        Integer founded,
        String logo,
        Long venueId,
        String venueName,
        String venueAddress,
        String venueCity,
        Integer venueCapacity,
        List<MatchResponse> recentMatches,
        List<MatchResponse> upcomingMatches
) {
}
