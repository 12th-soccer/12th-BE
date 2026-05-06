package com.example.be12th.domain.footballapi.service;

import com.example.be12th.domain.footballapi.presentation.dto.external.PlayerApiResponse;
import com.example.be12th.domain.footballapi.presentation.dto.external.TeamApiResponse;
import com.example.be12th.domain.footballapi.presentation.dto.external.PlayerSquadApiResponse;
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

    public PlayerApiResponse getPlayerDetail(Long playerId, int season) {
        return apiFootballRestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/players")
                        .queryParam("id", playerId)
                        .queryParam("season", season)
                        .build())
                .retrieve()
                .body(PlayerApiResponse.class);
    }

    public PlayerApiResponse getPlayersByLeague(Long leagueId, int season, int page) {
        return apiFootballRestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/players")
                        .queryParam("league", leagueId)
                        .queryParam("season", season)
                        .queryParam("page", page)
                        .build())
                .retrieve()
                .body(PlayerApiResponse.class);
    }

    public PlayerSquadApiResponse getPlayerSquad(Long teamId) {
        return apiFootballRestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/players/squads")
                        .queryParam("team", teamId)
                        .build())
                .retrieve()
                .body(PlayerSquadApiResponse.class);
    }

    public TeamApiResponse getTeamDetail(Long teamId) {
        return apiFootballRestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/teams")
                        .queryParam("id", teamId)
                        .build())
                .retrieve()
                .body(TeamApiResponse.class);
    }

    public TeamApiResponse getTeamsByLeague(Long leagueId, int season) {
        return apiFootballRestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/teams")
                        .queryParam("league", leagueId)
                        .queryParam("season", season)
                        .build())
                .retrieve()
                .body(TeamApiResponse.class);
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
