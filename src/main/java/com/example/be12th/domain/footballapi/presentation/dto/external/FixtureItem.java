package com.example.be12th.domain.footballapi.presentation.dto.external;

public record FixtureItem(
        FixtureInfo fixture,
        TeamsInfo teams,
        GoalsInfo goals
) {
}
