package com.example.be12th.domain.match.presentation;

import com.example.be12th.domain.match.presentation.dto.response.MatchResponse;
import com.example.be12th.domain.match.service.CaleanderQueryService;
import com.example.be12th.domain.match.service.MatchQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/match")
public class MatchController {

    private final MatchQueryService matchQueryService;
    private final CaleanderQueryService caleanderQueryService;

    @GetMapping("/{matchId}")
    @ResponseStatus(HttpStatus.OK)
    public MatchResponse query(@PathVariable Long matchId) {
        return matchQueryService.execute(matchId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MatchResponse> queryAll(@RequestParam LocalDate date) {
        return caleanderQueryService.execute(date);
    }
}
