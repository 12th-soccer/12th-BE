package com.example.be12th.domain.matchevent.presentation;

import com.example.be12th.domain.matchevent.presentation.dto.MatchEventResponse;
import com.example.be12th.domain.matchevent.service.MatctEventQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
public class MatchEventController {
    private final MatctEventQueryService matchEventQueryService;

    @GetMapping("{matchId}")
    @ResponseStatus(HttpStatus.OK)
    public List<MatchEventResponse> getMatchEvents(@PathVariable Long matchId) {
        return  matchEventQueryService.execute(matchId);
    }
}
