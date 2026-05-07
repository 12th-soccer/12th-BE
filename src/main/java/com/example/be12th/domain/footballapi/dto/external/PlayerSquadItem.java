package com.example.be12th.domain.footballapi.dto.external;

import java.util.List;

public record PlayerSquadItem(
        TeamInfo team,
        List<PlayerSquadInfo> players
) {
}
