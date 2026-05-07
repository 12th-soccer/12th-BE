package com.example.be12th.domain.match.presentation.dto.response;

import com.example.be12th.domain.footballapi.dto.external.FixtureItem;

public record MatchListResponse(
        Long matchId,
        String leagueType,
        String matchDate,
        String homeTeamName,
        String homeTeamImageUrl,
        String awayTeamName,
        String awayTeamImageUrl,
        Integer homeTeamScore,
        Integer awayTeamScore
) {
    public static MatchListResponse from(FixtureItem item) {
        return new MatchListResponse(
                item.fixture().id(),
                convertLeagueType(item.league().id()),
                item.fixture().date(),
                item.teams().home().name(),
                item.teams().home().logo(),
                item.teams().away().name(),
                item.teams().away().logo(),
                item.goals().home(),
                item.goals().away()
        );
    }

    private static String convertLeagueType(Long leagueId) {
        if (leagueId.equals(292L)) {
            return "K1";
        }
        if (leagueId.equals(293L)) {
            return "K2";
        }
        return "UNKNOWN";
    }
}
