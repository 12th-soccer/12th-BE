package com.example.be12th.domain.ranking.presentation.dto.external;

import java.util.List;

public record StandingApiResponse(
        List<StandingLeagueWrapper> response
) {
}
