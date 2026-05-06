package com.example.be12th.domain.footballapi.service;

import com.example.be12th.domain.footballapi.presentation.dto.external.EventApiResponse;
import com.example.be12th.domain.footballapi.presentation.dto.external.FixtureApiResponse;
import com.example.be12th.domain.footballapi.presentation.dto.external.FixtureItem;
import com.example.be12th.domain.footballapi.presentation.dto.external.LineupApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FootballClient {

    private final RestClient apiFootballRestClient;

    public FixtureApiResponse getFixtures(Long leagueId, int season, String date) {
        return apiFootballRestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/fixtures")
                        .queryParam("league", leagueId)
                        .queryParam("season", season)
                        .queryParam("date", date)
                        .build())
                .retrieve()
                .body(FixtureApiResponse.class);
    }

    public FixtureApiResponse getFixtureDetail(Long matchId) {
        return apiFootballRestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/fixtures")
                        .queryParam("id", matchId)
                        .build())
                .retrieve()
                .body(FixtureApiResponse.class);
    }

    public EventApiResponse getFixtureEvents(Long matchId) {
        return apiFootballRestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/fixtures/events")
                        .queryParam("fixture", matchId)
                        .build())
                .retrieve()
                .body(EventApiResponse.class);
    }

    public LineupApiResponse getFixtureLineups(Long matchId) {
        return apiFootballRestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/fixtures/lineups")
                        .queryParam("fixture", matchId)
                        .build())
                .retrieve()
                .body(LineupApiResponse.class);
    }

    public FixtureApiResponse getFixturesByRange(Long leagueId, int season, String from, String to) {
        return apiFootballRestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/fixtures")
                        .queryParam("league", leagueId)
                        .queryParam("season", season)
                        .queryParam("from", from)
                        .queryParam("to", to)
                        .build())
                .retrieve()
                .body(FixtureApiResponse.class);
    }

    public List<FixtureItem> getFixturesForSeason(Long leagueId, int season) {
        FixtureApiResponse result = apiFootballRestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/fixtures")
                        .queryParam("league", leagueId)
                        .queryParam("season", season)
                        .build())
                .retrieve()
                .body(FixtureApiResponse.class);

        if (result == null || result.response() == null) {
            return List.of();
        }

        return result.response();
    }
}
