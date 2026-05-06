package com.example.be12th.domain.footballapi.domain;

import com.example.be12th.domain.footballapi.presentation.dto.response.MatchDetailResponse;
import com.example.be12th.domain.footballapi.presentation.dto.response.MatchResponse;
import com.example.be12th.domain.footballapi.service.KLeagueMatchDetailQueryService;
import com.example.be12th.domain.footballapi.service.MatchQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/matches")
public class MatchController {

    private final MatchQueryService matchQueryService;
    private final KLeagueMatchDetailQueryService kLeagueMatchDetailQueryService;

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
