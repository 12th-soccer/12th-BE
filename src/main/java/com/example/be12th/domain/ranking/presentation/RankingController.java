package com.example.be12th.domain.ranking.presentation;

import com.example.be12th.domain.league.LeagueType;
import com.example.be12th.domain.ranking.presentation.dto.response.RankingResponse;
import com.example.be12th.domain.ranking.service.RankingQueryAllService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ranking")
public class RankingController {
    private final RankingQueryAllService rankingQueryAllService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<RankingResponse> getRankings(@RequestParam LeagueType leagueType) {
        return rankingQueryAllService.execute(leagueType);
    }
}
