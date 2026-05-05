package com.example.be12th.domain.footballapi.presentation.dto.response;

public record MatchListResponse(
        Long fixtureId,
        Long leagueId,
        String leagueName,
        String leagueLogo,
        String matchDate,
        String statusCode,
        Long venueId,
        String venueName,
        String venueCity,
        Long homeTeamId,
        String homeTeamName,
        String homeTeamLogo,
        Long awayTeamId,
        String awayTeamName,
        String awayTeamLogo,
        Integer homeScore,
        Integer awayScore
) {
}