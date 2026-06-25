package com.example.be12th.domain.team.service;

import com.example.be12th.domain.footballapi.client.FootballClient;
import com.example.be12th.domain.footballapi.dto.external.TeamApiResponse;
import com.example.be12th.domain.footballapi.dto.external.TeamDetailItem;
import com.example.be12th.domain.team.domain.Team;
import com.example.be12th.domain.team.domain.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamSyncService {

    private final TeamRepository teamRepository;
    private final FootballClient footballClient;

    @Transactional
    public void execute(Long leagueId, int season) {
        TeamApiResponse result = footballClient.getTeamsByLeague(leagueId, season);

        if (result == null || result.response() == null) {
            return;
        }

        for (TeamDetailItem item : result.response()) {
            if (!isValid(item)) {
                continue;
            }

            upsertTeam(leagueId, season, item);
        }
    }

    private void upsertTeam(Long leagueId, int season, TeamDetailItem item) {
        Long externalTeamId = item.team().id();

        Team team = teamRepository
                .findByExternalTeamIdAndLeagueIdAndSeason(externalTeamId, leagueId, season)
                .orElseGet(() -> Team.builder()
                        .externalTeamId(externalTeamId)
                        .leagueId(leagueId)
                        .season(season)
                        .name(item.team().name())
                        .build());

        team.update(
                item.team().name(),
                item.team().country(),
                item.team().founded(),
                item.team().logo(),
                item.venue() == null ? null : item.venue().id(),
                item.venue() == null ? null : item.venue().name(),
                item.venue() == null ? null : item.venue().address(),
                item.venue() == null ? null : item.venue().city(),
                item.venue() == null ? null : item.venue().capacity()
        );

        teamRepository.save(team);
    }

    private boolean isValid(TeamDetailItem item) {
        return item != null
                && item.team() != null
                && item.team().id() != null
                && item.team().name() != null;
    }
}
