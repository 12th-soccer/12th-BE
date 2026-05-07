package com.example.be12th.domain.footballapi.dto.external;

public record PlayerStatisticItem(
        LeagueInfo league,
        TeamInfo team,
        PlayerGamesInfo games,
        PlayerGoalsInfo goals
) {
}
