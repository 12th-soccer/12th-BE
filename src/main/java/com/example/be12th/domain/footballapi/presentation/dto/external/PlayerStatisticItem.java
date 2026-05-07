package com.example.be12th.domain.footballapi.presentation.dto.external;

import com.example.be12th.domain.goal.presentation.dto.PlayerGoalsInfo;

public record PlayerStatisticItem(
        LeagueInfo league,
        TeamInfo team,
        PlayerGamesInfo games,
        PlayerGoalsInfo goals
) {
}
