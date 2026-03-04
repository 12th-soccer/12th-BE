package com.example.be12th.domain.match.presentation;

import com.example.be12th.domain.match.presentation.dto.response.MatchResponse;
import com.example.be12th.domain.match.service.MatchQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/match")
public class MatchController {

    private final MatchQueryService matchQueryService;

    @GetMapping("/{matchId}")
    public MatchResponse query(@PathVariable Long matchId) {
        return matchQueryService.execute(matchId);
    }
}
