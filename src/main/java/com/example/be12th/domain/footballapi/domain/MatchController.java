package com.example.be12th.domain.footballapi.domain;

import com.example.be12th.domain.footballapi.presentation.dto.response.MatchDetailResponse;
import com.example.be12th.domain.footballapi.presentation.dto.response.MatchResponse;
import com.example.be12th.domain.footballapi.presentation.dto.response.PlayerResponse;
import com.example.be12th.domain.footballapi.presentation.dto.response.TeamResponse;
import com.example.be12th.domain.footballapi.service.KLeagueMatchDetailQueryService;
import com.example.be12th.domain.footballapi.service.MatchQueryService;
import com.example.be12th.domain.footballapi.service.PlayerQueryService;
import com.example.be12th.domain.footballapi.service.TeamQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/matches")
public class MatchController {

    private final MatchQueryService matchQueryService;
    private final KLeagueMatchDetailQueryService kLeagueMatchDetailQueryService;
    private final PlayerQueryService playerQueryService;
    private final TeamQueryService teamQueryService;

    @GetMapping("/kleague1")
    public List<MatchResponse> getKLeague1Matches(@RequestParam int season, @RequestParam String date) {
        return matchQueryService.getKLeague1Matches(season, date);
    }

    @GetMapping("/kleague2")
    public List<MatchResponse> getKLeague2Matches(@RequestParam int season, @RequestParam String date) {
        return matchQueryService.getKLeague2Matches(season, date);
    }

    @GetMapping("/{matchId}")
    public MatchDetailResponse getMatchDetail(@PathVariable Long matchId) {
        return kLeagueMatchDetailQueryService.getMatchDetail(matchId);
    }

    @GetMapping("/players/{playerId}")
    public PlayerResponse getPlayerById(@PathVariable Long playerId, @RequestParam int season) {
        return playerQueryService.execute(playerId, season);
    }

    @GetMapping("/players/kleague1")
    public List<PlayerResponse> getKLeague1Players(@RequestParam int season, @RequestParam(defaultValue = "1") int page) {
        return playerQueryService.getKLeague1Players(season, page);
    }

    @GetMapping("/players/kleague2")
    public List<PlayerResponse> getKLeague2Players(@RequestParam int season, @RequestParam(defaultValue = "1") int page) {
        return playerQueryService.getKLeague2Players(season, page);
    }

    @GetMapping("/teams/{teamId}")
    public TeamResponse getTeamById(@PathVariable Long teamId) {
        return teamQueryService.execute(teamId);
    }

    @GetMapping("/teams/kleague1")
    public List<TeamResponse> getKLeague1Teams(@RequestParam int season) {
        return teamQueryService.getKLeague1Teams(season);
    }

    @GetMapping("/teams/kleague2")
    public List<TeamResponse> getKLeague2Teams(@RequestParam int season) {
        return teamQueryService.getKLeague2Teams(season);
    }
}
