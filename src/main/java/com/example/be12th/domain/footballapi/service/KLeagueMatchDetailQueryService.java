package com.example.be12th.domain.footballapi.service;

import com.example.be12th.domain.footballapi.presentation.dto.external.EventApiResponse;
import com.example.be12th.domain.footballapi.presentation.dto.external.EventItem;
import com.example.be12th.domain.footballapi.presentation.dto.external.FixtureApiResponse;
import com.example.be12th.domain.footballapi.presentation.dto.external.FixtureItem;
import com.example.be12th.domain.footballapi.presentation.dto.external.LineupApiResponse;
import com.example.be12th.domain.footballapi.presentation.dto.external.LineupItem;
import com.example.be12th.domain.footballapi.presentation.dto.response.LineupResponse;
import com.example.be12th.domain.footballapi.presentation.dto.response.MatchDetailResponse;
import com.example.be12th.domain.footballapi.presentation.dto.response.MatchEventResponse;
import com.example.be12th.domain.footballapi.presentation.dto.response.MatchListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KLeagueMatchDetailQueryService {

    private final FootballClient footballClient;

    public MatchDetailResponse getMatchDetail(Long matchId) {
        FixtureApiResponse fixtureResult = footballClient.getFixtureDetail(matchId);

        if (fixtureResult == null || fixtureResult.response() == null || fixtureResult.response().isEmpty()) {
            return null;
        }

        FixtureItem fixtureItem = fixtureResult.response().get(0);

        List<MatchEventResponse> events = extractEvents(matchId);
        List<LineupResponse> lineups = extractLineups(matchId);

        return new MatchDetailResponse(
                MatchListResponse.from(fixtureItem),
                events,
                lineups
        );
    }

    private List<MatchEventResponse> extractEvents(Long matchId) {
        EventApiResponse result = footballClient.getFixtureEvents(matchId);

        if (result == null || result.response() == null) {
            return List.of();
        }

        return result.response().stream()
                .map(this::toMatchEventResponse)
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

    private MatchEventResponse toMatchEventResponse(EventItem item) {
        String time = item.time() == null || item.time().elapsed() == null
                ? null
                : item.time().extra() == null
                ? String.valueOf(item.time().elapsed())
                : item.time().elapsed() + "+" + item.time().extra();

        return new MatchEventResponse(
                time,
                item.type(),
                item.detail(),
                item.team() == null ? null : item.team().id(),
                item.team() == null ? null : item.team().name(),
                item.player() == null ? null : item.player().id(),
                item.player() == null ? null : item.player().name(),
                item.assist() == null ? null : item.assist().id(),
                item.assist() == null ? null : item.assist().name()
        );
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
