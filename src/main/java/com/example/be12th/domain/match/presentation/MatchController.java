package com.example.be12th.domain.match.presentation;

import com.example.be12th.domain.match.presentation.dto.response.MatchDetailResponse;
import com.example.be12th.domain.match.presentation.dto.response.MatchResponse;
import com.example.be12th.domain.match.service.KLeagueMatchDetailQueryService;
import com.example.be12th.domain.match.service.MatchQueryService;
import com.example.be12th.domain.match.service.MatchSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/match")
public class MatchController {

    private final MatchQueryService matchQueryService;
    private final KLeagueMatchDetailQueryService kLeagueMatchDetailQueryService;
    private final MatchSyncService matchSyncService;

    @PostMapping("/sync")
    public void syncMatches(@RequestParam Long leagueId, @RequestParam int season) {
        matchSyncService.execute(leagueId, season);
    }

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
}
