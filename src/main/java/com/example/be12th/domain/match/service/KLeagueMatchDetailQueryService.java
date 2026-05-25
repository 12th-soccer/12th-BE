package com.example.be12th.domain.match.service;

import com.example.be12th.domain.footballapi.dto.external.EventApiResponse;
import com.example.be12th.domain.footballapi.dto.external.EventItem;
import com.example.be12th.domain.footballapi.dto.external.FixtureApiResponse;
import com.example.be12th.domain.footballapi.dto.external.FixtureItem;
import com.example.be12th.domain.footballapi.dto.external.LineupApiResponse;
import com.example.be12th.domain.footballapi.dto.external.LineupItem;
import com.example.be12th.domain.footballapi.dto.external.PlayerApiResponse;
import com.example.be12th.domain.footballapi.dto.external.PlayerItem;
import com.example.be12th.domain.footballapi.client.FootballClient;
import com.example.be12th.domain.match.presentation.dto.response.*;
import com.example.be12th.domain.spoiler.service.SpoilerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KLeagueMatchDetailQueryService {

    private final FootballClient footballClient;
    private final SpoilerService spoilerService;

    public MatchDetailResponse getMatchDetail(Long matchId) {
        FixtureApiResponse fixtureResult = footballClient.getFixtureDetail(matchId);

        if (fixtureResult == null || fixtureResult.response() == null || fixtureResult.response().isEmpty()) {
            return null;
        }

        FixtureItem fixtureItem = fixtureResult.response().get(0);

        MatchListResponse match = MatchListResponse.from(fixtureItem);

        if(spoilerService.isTodayMatch(match.matchDate()) && spoilerService.isSpoilerEnabled()){
                match = new MatchListResponse(
                        match.matchId(),
                        match.leagueType(),
                        match.matchDate(),
                        match.homeTeamName(),
                        match.homeTeamId(),
                        match.homeTeamImageUrl(),
                        match.awayTeamName(),
                        match.awayTeamId(),
                        match.awayTeamImageUrl(),
                        null,
                        null
                );
            }

        Integer season = fixtureItem.league() == null ? null : fixtureItem.league().season();
        List<MatchEventResponse> events = extractEvents(matchId, season);
        List<LineupResponse> lineups = extractLineups(matchId);

        return new MatchDetailResponse(
                match,
                events,
                lineups
        );
    }

    private List<MatchEventResponse> extractEvents(Long matchId, Integer season) {
        EventApiResponse result = footballClient.getFixtureEvents(matchId);

        if (result == null || result.response() == null) {
            return List.of();
        }

        Map<Long, String> playerImageUrlCache = new HashMap<>();

        return result.response().stream()
                .map(item -> toMatchEventResponse(item, season, playerImageUrlCache))
                .toList();
    }

    private List<LineupResponse> extractLineups(Long matchId) {
        LineupApiResponse result = footballClient.getFixtureLineups(matchId);

        if (result == null || result.response() == null) {
            return List.of();
        }

        return result.response().stream()
                .map(this::toLineupResponse)
                .toList();
    }

    private MatchEventResponse toMatchEventResponse(EventItem item, Integer season, Map<Long, String> playerImageUrlCache) {
        String time = item.time() == null || item.time().elapsed() == null
                ? null
                : item.time().extra() == null
                ? String.valueOf(item.time().elapsed())
                : item.time().elapsed() + "+" + item.time().extra();
        Long playerId = item.player() == null ? null : item.player().id();

        return new MatchEventResponse(
                time,
                item.type(),
                item.detail(),
                item.team() == null ? null : item.team().id(),
                item.team() == null ? null : item.team().name(),
                playerId,
                item.player() == null ? null : item.player().name(),
                findPlayerImageUrl(playerId, season, playerImageUrlCache),
                item.assist() == null ? null : item.assist().id(),
                item.assist() == null ? null : item.assist().name()
        );
    }

    private String findPlayerImageUrl(Long playerId, Integer season, Map<Long, String> playerImageUrlCache) {
        if (playerId == null || season == null) {
            return null;
        }

        if (playerImageUrlCache.containsKey(playerId)) {
            return playerImageUrlCache.get(playerId);
        }

        PlayerApiResponse result = footballClient.getPlayerDetail(playerId, season);
        String imageUrl = null;

        if (result != null && result.response() != null && !result.response().isEmpty()) {
            PlayerItem playerItem = result.response().get(0);
            if (playerItem != null && playerItem.player() != null) {
                imageUrl = playerItem.player().photo();
            }
        }

        playerImageUrlCache.put(playerId, imageUrl);
        return imageUrl;
    }

    private LineupResponse toLineupResponse(LineupItem item) {
        return new LineupResponse(
                item.team() == null ? null : item.team().id(),
                item.team() == null ? null : item.team().name(),
                item.formation(),
                item.coach() == null ? null : item.coach().name()
        );
    }
}
