package com.example.be12th.domain.match.presentation;

import com.example.be12th.domain.match.presentation.dto.response.MatchResponse;
import com.example.be12th.domain.match.service.MatchQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/match")
public class MatchController {

    private final MatchQueryService matchQueryService;

    @GetMapping("/{matchId}")
    @ResponseStatus(HttpStatus.OK)
    public MatchResponse query(@PathVariable Long matchId) {
        return matchQueryService.execute(matchId);
    }
}
