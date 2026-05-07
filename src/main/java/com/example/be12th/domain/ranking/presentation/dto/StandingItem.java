package com.example.be12th.domain.ranking.presentation.dto;

import com.example.be12th.domain.footballapi.presentation.dto.external.TeamInfo;

public record StandingItem(
        Integer rank,
        TeamInfo team,
        Integer points,
        String group,
        String form,
        StandingAllStats all
) {
}
