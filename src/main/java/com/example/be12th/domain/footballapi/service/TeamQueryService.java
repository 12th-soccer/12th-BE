package com.example.be12th.domain.footballapi.service;

import com.example.be12th.domain.footballapi.presentation.dto.external.TeamApiResponse;
import com.example.be12th.domain.footballapi.presentation.dto.external.TeamDetailItem;
import com.example.be12th.domain.footballapi.presentation.dto.response.TeamResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamQueryService {

    private final FootballClient footballClient;

    public TeamResponse execute(Long teamId) {
        TeamApiResponse result = footballClient.getTeamDetail(teamId);

        if (result == null || result.response() == null || result.response().isEmpty()) {
            return null;
        }

        return toTeamResponse(result.response().get(0));
    }

    public List<TeamResponse> getKLeague1Teams(int season) {
        return getTeamsByLeague(KLeagueConstants.KLEAGUE1_ID, season);
    }

    public List<TeamResponse> getKLeague2Teams(int season) {
        return getTeamsByLeague(KLeagueConstants.KLEAGUE2_ID, season);
    }

    private List<TeamResponse> getTeamsByLeague(Long leagueId, int season) {
        TeamApiResponse result = footballClient.getTeamsByLeague(leagueId, season);

        if (result == null || result.response() == null) {
            return List.of();
        }

        return result.response().stream()
                .map(this::toTeamResponse)
                .filter(team -> team != null && team.teamId() != null)
                .toList();
    }

    private TeamResponse toTeamResponse(TeamDetailItem item) {
        if (item == null) {
            return null;
        }

        return new TeamResponse(
                item.team() == null ? null : item.team().id(),
                item.team() == null ? null : item.team().name(),
                item.team() == null ? null : item.team().country(),
                item.team() == null ? null : item.team().founded(),
                item.team() == null ? null : item.team().logo(),
                item.venue() == null ? null : item.venue().id(),
                item.venue() == null ? null : item.venue().name(),
                item.venue() == null ? null : item.venue().address(),
                item.venue() == null ? null : item.venue().city(),
                item.venue() == null ? null : item.venue().capacity()
        );
    }
}
