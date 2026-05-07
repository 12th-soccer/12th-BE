package com.example.be12th.domain.footballapi.presentation.dto.external;

import java.util.List;

public record PlayerSquadItem(
        TeamInfo team,
        List<PlayerSquadInfo> players
) {
}
