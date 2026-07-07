package com.example.be12th.domain.match.service;

import com.example.be12th.domain.footballapi.client.FootballClient;
import com.example.be12th.domain.footballapi.dto.external.FixtureItem;
import com.example.be12th.domain.match.domain.FootballMatch;
import com.example.be12th.domain.match.domain.repository.FootballMatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchSyncService {

    private final FootballMatchRepository footballMatchRepository;
    private final FootballClient footballClient;
    private final TransactionTemplate transactionTemplate;

    public void execute(Long leagueId, int season) {
        List<FixtureItem> fixtures = footballClient.getFixturesForSeason(leagueId, season);

        transactionTemplate.executeWithoutResult(status -> syncMatches(leagueId, season, fixtures));
    }

    private void syncMatches(Long leagueId, int season, List<FixtureItem> fixtures) {
        for (FixtureItem item : fixtures) {
            if (!isValid(item)) {
                continue;
            }

            upsertMatch(leagueId, season, item);
        }
    }

    private void upsertMatch(Long leagueId, int season, FixtureItem item) {
        Long externalFixtureId = item.fixture().id();

        FootballMatch match = footballMatchRepository.findByExternalFixtureId(externalFixtureId)
                .orElseGet(() -> FootballMatch.builder()
                        .externalFixtureId(externalFixtureId)
                        .leagueId(leagueId)
                        .season(season)
                        .matchDate(item.fixture().date())
                        .build());

        match.update(
                item.fixture().date(),
                item.teams().home().id(),
                item.teams().home().name(),
                item.teams().home().logo(),
                item.teams().away().id(),
                item.teams().away().name(),
                item.teams().away().logo(),
                item.goals() == null ? null : item.goals().home(),
                item.goals() == null ? null : item.goals().away()
        );

        footballMatchRepository.save(match);
    }

    private boolean isValid(FixtureItem item) {
        return item != null
                && item.fixture() != null
                && item.fixture().id() != null
                && item.fixture().date() != null
                && item.teams() != null
                && item.teams().home() != null
                && item.teams().away() != null;
    }
}
