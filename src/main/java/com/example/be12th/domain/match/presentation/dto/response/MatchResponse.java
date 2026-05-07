package com.example.be12th.domain.match.presentation.dto.response;

import com.example.be12th.domain.footballapi.presentation.dto.external.FixtureItem;

public record MatchResponse(
        Long matchId,
        String matchDate,
        String homeTeam,
        String homeTeamLogo,
        String awayTeam,
        String awayTeamLogo,
        Integer homeScore,
        Integer awayScore
) {
    public static MatchResponse from(FixtureItem item) {
        return new MatchResponse(
                item.fixture().id(),
                item.fixture().date(),
                item.teams().home().name(),
                item.teams().home().logo(),
                item.teams().away().name(),
                item.teams().away().logo(),
                item.goals().home(),
                item.goals().away()
        );
    }
}