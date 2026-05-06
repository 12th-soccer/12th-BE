package com.example.be12th.domain.footballapi.presentation.dto.external;

public record EventItem(
        EventTimeInfo time,
        TeamInfo team,
        EventPlayerInfo player,
        EventPlayerInfo assist,
        String type,
        String detail
) {
}
