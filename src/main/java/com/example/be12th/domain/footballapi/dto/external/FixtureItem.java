package com.example.be12th.domain.footballapi.dto.external;

public record FixtureItem(
        FixtureInfo fixture,
        LeagueInfo league,
        TeamsInfo teams,
        GoalsInfo goals
) {
}
