package com.example.be12th.domain.ranking.service;

import com.example.be12th.domain.footballapi.client.FootballClient;
import com.example.be12th.domain.footballapi.support.KLeagueConstants;
import com.example.be12th.domain.ranking.presentation.dto.RankingResponse;
import com.example.be12th.domain.ranking.presentation.dto.StandingApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingQueryService {
    private final FootballClient footballClient;

    public List<RankingResponse> execute(int season, String league) {
        Long leagueId = KLeagueConstants.resolveLeagueId(league);
        StandingApiResponse result = footballClient.getStandings(leagueId, season);

        if (result == null || result.response() == null || result.response().isEmpty()) {
            return List.of();
        }

        return result.response().get(0).league().standings().get(0).stream()
                .map(RankingResponse::from)
                .toList();
    }
}
