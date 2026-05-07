package com.example.be12th.domain.ranking.presentation.dto.external;

import com.example.be12th.domain.footballapi.dto.external.TeamInfo;

public record StandingItem(
        Integer rank,
        TeamInfo team,
        Integer points,
        String group,
        String form,
        StandingAllStats all
) {
}
