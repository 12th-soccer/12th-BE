package com.example.be12th.domain.match.service;

import com.example.be12th.domain.footballapi.presentation.dto.external.FixtureApiResponse;
import com.example.be12th.domain.footballapi.presentation.dto.external.FixtureItem;
import com.example.be12th.domain.match.presentation.dto.response.MatchResponse;
import com.example.be12th.domain.footballapi.service.FootballClient;
import com.example.be12th.domain.footballapi.service.KLeagueConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchQueryService {

    private final FootballClient apiFootballClient;

    public List<MatchResponse> getKLeague1Matches(int season, String date) {
        return getMatches(KLeagueConstants.KLEAGUE1_ID, season, date);
    }

    public List<MatchResponse> getKLeague2Matches(int season, String date) {
        return getMatches(KLeagueConstants.KLEAGUE2_ID, season, date);
    }

    private List<MatchResponse> getMatches(Long leagueId, int season, String date) {
        List<FixtureItem> fixtures = extractFixtures(
                apiFootballClient.getFixtures(leagueId, season, date)
        );

        if (fixtures.isEmpty()) {
            fixtures = extractFixtures(
                    apiFootballClient.getFixturesByRange(leagueId, season, date, date)
            );
        }

        if (fixtures.isEmpty()) {
            fixtures = apiFootballClient.getFixturesForSeason(leagueId, season).stream()
                    .filter(item -> item.fixture() != null)
                    .filter(item -> item.fixture().date() != null)
                    .filter(item -> item.fixture().date().startsWith(date))
                    .toList();
        }

        return fixtures.stream()
                .map(MatchResponse::from)
                .toList();
    }

    private List<FixtureItem> extractFixtures(FixtureApiResponse result) {
        if (result == null || result.response() == null) {
            return List.of();
        }

        return result.response();
    }
}
