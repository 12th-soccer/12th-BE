package com.example.be12th.domain.ranking.presentation;

import com.example.be12th.domain.ranking.presentation.dto.RankingResponse;
import com.example.be12th.domain.ranking.service.RankingQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ranking")
public class RankingController {

    private final RankingQueryService rankingQueryService;

    @GetMapping
    public List<RankingResponse> getRanking(@RequestParam int season,@RequestParam String league) {
        return rankingQueryService.execute(season, league);
    }
}
