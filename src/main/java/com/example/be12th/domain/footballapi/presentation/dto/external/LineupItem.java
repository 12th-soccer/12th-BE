package com.example.be12th.domain.footballapi.presentation.dto.external;

public record LineupItem(
        TeamInfo team,
        LineupCoachInfo coach,
        String formation
) {
}
