package com.example.be12th.domain.footballapi.dto.external;

public record LineupItem(
        TeamInfo team,
        LineupCoachInfo coach,
        String formation
) {
}
