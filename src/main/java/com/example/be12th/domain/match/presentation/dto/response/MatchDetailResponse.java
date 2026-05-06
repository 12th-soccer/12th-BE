package com.example.be12th.domain.match.presentation.dto.response;

import java.util.List;

public record MatchDetailResponse(
        MatchListResponse match,
        List<MatchEventResponse> events,
        List<LineupResponse> lineups
) {
}