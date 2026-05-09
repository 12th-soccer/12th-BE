package com.example.be12th.domain.team.service;

import com.example.be12th.domain.footballapi.client.FootballClient;
import com.example.be12th.domain.footballapi.dto.external.FixtureItem;
import com.example.be12th.domain.footballapi.dto.external.TeamApiResponse;
import com.example.be12th.domain.footballapi.dto.external.TeamDetailItem;
import com.example.be12th.domain.footballapi.support.KLeagueConstants;
import com.example.be12th.domain.match.presentation.dto.response.MatchResponse;
import com.example.be12th.domain.team.presentation.dto.response.TeamDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.Year;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamDetailQueryService {

    private final FootballClient footballClient;

    public TeamDetailResponse execute(Long teamId, Integer season) {
        TeamApiResponse result = footballClient.getTeamDetail(teamId);

        if (result == null || result.response() == null || result.response().isEmpty()) {
            return null;
        }

        int resolvedSeason = season == null
                ? Year.now(ZoneId.of("Asia/Seoul")).getValue()
                : season;

        TeamDetailItem item = result.response().get(0);
        List<FixtureItem> fixtures = getTeamFixtures(teamId, resolvedSeason);

        return new TeamDetailResponse(
                item.team() == null ? null : item.team().id(),
                item.team() == null ? null : item.team().name(),
                item.team() == null ? null : item.team().country(),
                item.team() == null ? null : item.team().founded(),
                item.team() == null ? null : item.team().logo(),
                item.venue() == null ? null : item.venue().id(),
                item.venue() == null ? null : item.venue().name(),
                item.venue() == null ? null : item.venue().address(),
                item.venue() == null ? null : item.venue().city(),
                item.venue() == null ? null : item.venue().capacity(),
                getRecentMatches(fixtures),
                getUpcomingMatches(fixtures)
        );
    }

    private List<FixtureItem> getTeamFixtures(Long teamId, int season) {
        LinkedHashMap<Long, FixtureItem> uniqueFixtures = new LinkedHashMap<>();

        collectTeamFixtures(uniqueFixtures, teamId, season, KLeagueConstants.KLEAGUE1_ID);
        collectTeamFixtures(uniqueFixtures, teamId, season, KLeagueConstants.KLEAGUE2_ID);

        return uniqueFixtures.values().stream().toList();
    }

    private void collectTeamFixtures(LinkedHashMap<Long, FixtureItem> uniqueFixtures, Long teamId, int season, Long leagueId) {
        footballClient.getFixturesForSeason(leagueId, season).stream()
                .filter(this::hasRequiredFixtureData)
                .filter(item -> isTeamFixture(item, teamId))
                .forEach(item -> uniqueFixtures.put(item.fixture().id(), item));
    }

    private List<MatchResponse> getRecentMatches(List<FixtureItem> fixtures) {
        Instant now = Instant.now();

        return fixtures.stream()
                .filter(item -> parseFixtureInstant(item).isBefore(now))
                .sorted(Comparator.comparing(this::parseFixtureInstant).reversed())
                .limit(3)
                .map(MatchResponse::from)
                .toList();
    }

    private List<MatchResponse> getUpcomingMatches(List<FixtureItem> fixtures) {
        Instant now = Instant.now();

        return fixtures.stream()
                .filter(item -> !parseFixtureInstant(item).isBefore(now))
                .sorted(Comparator.comparing(this::parseFixtureInstant))
                .limit(3)
                .map(MatchResponse::from)
                .toList();
    }

    private boolean isTeamFixture(FixtureItem item, Long teamId) {
        return item.teams().home().id().equals(teamId) || item.teams().away().id().equals(teamId);
    }

    private boolean hasRequiredFixtureData(FixtureItem item) {
        return item != null
                && item.fixture() != null
                && item.fixture().id() != null
                && item.fixture().date() != null
                && item.teams() != null
                && item.teams().home() != null
                && item.teams().home().id() != null
                && item.teams().away() != null
                && item.teams().away().id() != null;
    }

    private Instant parseFixtureInstant(FixtureItem item) {
        return Instant.parse(item.fixture().date());
    }
}
