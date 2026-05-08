package com.example.be12th.domain.search.service;

import com.example.be12th.domain.search.presentation.dto.TeamSearchResponse;
import com.example.be12th.domain.team.presentation.dto.response.TeamResponse;
import com.example.be12th.domain.team.service.TeamQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class TeamSearchService {
    private final TeamQueryService teamQueryService;

    public List<TeamSearchResponse> execute(String keyword, int season) {
        List<TeamResponse> kLeague1Teams = teamQueryService.getKLeague1Teams(season);
        List<TeamResponse> kLeague2Teams = teamQueryService.getKLeague2Teams(season);

        String normalizedKeyword = keyword.toLowerCase(Locale.ROOT);

        return Stream.concat(kLeague1Teams.stream(), kLeague2Teams.stream())
                .filter(team -> team.name() != null)
                .filter(team -> team.name().toLowerCase(Locale.ROOT).contains(normalizedKeyword))
                .map(team -> new TeamSearchResponse(
                        team.teamId(),
                        team.name(),
                        team.logo()
                ))
                .toList();
    }
}
