package com.example.be12th.domain.footballapi.presentation.dto.response;

import com.example.be12th.domain.footballapi.presentation.dto.external.FixtureItem;

public record MatchResponse(
        Long matchId,
        String matchDate,
        String homeTeam,
        String awayTeam,
        Integer homeScore,
        Integer awayScore
) {
    public static MatchResponse from(FixtureItem item) {
        return new MatchResponse(
                item.fixture().id(),
                item.fixture().date(),
                item.teams().home().name(),
                item.teams().away().name(),
                item.goals().home(),
                item.goals().away()
        );
    }
}
