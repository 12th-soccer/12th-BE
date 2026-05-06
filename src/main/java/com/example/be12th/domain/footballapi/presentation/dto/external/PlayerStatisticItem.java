package com.example.be12th.domain.footballapi.presentation.dto.external;

public record PlayerStatisticItem(
        LeagueInfo league,
        TeamInfo team,
        PlayerGamesInfo games
) {
}
