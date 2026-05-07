package com.example.be12th.domain.goal.service;

import com.example.be12th.domain.footballapi.client.FootballClient;
import com.example.be12th.domain.footballapi.presentation.dto.external.EventApiResponse;
import com.example.be12th.domain.footballapi.presentation.dto.external.FixtureItem;
import com.example.be12th.domain.footballapi.support.KLeagueConstants;
import com.example.be12th.domain.goal.presentation.dto.PlayerGoalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalQueryService {
    private final FootballClient footballClient;

    public List<PlayerGoalResponse> execute(Long playerId, int season, String league){
        Long leagueId = KLeagueConstants.resolveLeagueId(league);
        List<FixtureItem> fixtures = footballClient.getFixturesForSeason(leagueId, season);

        return fixtures.stream()
                .flatMap(fixture -> extractGoalEvents(fixture, playerId).stream())
                .toList();
    }

    private List<PlayerGoalResponse> extractGoalEvents(FixtureItem fixture, Long playerId) {
        EventApiResponse result = footballClient.getFixtureEvents(fixture.fixture().id());

        if (result == null || result.response() == null) {
            return List.of();
        }

        return result.response().stream()
                .filter(event -> event.player() != null)
                .filter(event -> event.player().id() != null)
                .filter(event -> event.player().id().equals(playerId))
                .filter(event -> "Goal".equalsIgnoreCase(event.type()))
                .map(event -> new PlayerGoalResponse(
                        fixture.fixture().id(),
                        event.player().id(),
                        event.player().name(),
                        fixture.fixture().date(),
                        event.time() == null ? null : event.time().elapsed(),
                        event.detail()
                ))
                .toList();
    }
}
