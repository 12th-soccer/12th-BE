package com.example.be12th.domain.footballapi.domain;

import com.example.be12th.domain.footballapi.presentation.dto.response.MatchResponse;
import com.example.be12th.domain.footballapi.service.MatchQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/matches")
public class MatchController {

    private final MatchQueryService matchQueryService;

    @GetMapping
    public List<MatchResponse> getMatches(@RequestParam String date) {
        return matchQueryService.getMatches(date);
    }
}