package com.example.be12th.domain.search.presentation;

import com.example.be12th.domain.search.presentation.dto.PlayerSearchResponse;
import com.example.be12th.domain.search.presentation.dto.TeamSearchResponse;
import com.example.be12th.domain.search.service.PlayerSearchService;
import com.example.be12th.domain.search.service.TeamSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {
    private final PlayerSearchService playerSearchService;
    private final TeamSearchService teamSearchService;

    @GetMapping("/player")
    public List<PlayerSearchResponse> searchPlayers(@RequestParam String keyword, @RequestParam int season, @RequestParam(defaultValue = "1") int page) {
        return playerSearchService.execute(keyword, season, page);
    }

    @GetMapping("/team")
    public List<TeamSearchResponse> searchTeam(@RequestParam String keyword, @RequestParam int season) {
        return teamSearchService.execute(keyword, season);
    }
}
