package com.example.be12th.domain.ranking.presentation;

import com.example.be12th.domain.ranking.presentation.dto.response.RankingResponse;
import com.example.be12th.domain.ranking.service.RankingQueryAllService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ranking")
public class RankingController {
    private final RankingQueryAllService rankingQueryAllService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RankingResponse> getRankings() {
        return rankingQueryAllService.execute();
    }
}
