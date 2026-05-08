package com.example.be12th.domain.search.presentation;

import com.example.be12th.domain.player.presentation.dto.response.PlayerResponse;
import com.example.be12th.domain.search.presentation.dto.PlayerSearchResponse;
import com.example.be12th.domain.search.service.PlayerSearchService;
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

    @GetMapping
    public List<PlayerSearchResponse> searchPlayers(@RequestParam String keyword, @RequestParam int season, @RequestParam(defaultValue = "1") int page) {
        return playerSearchService.searchPlayers(keyword, season, page);
    }
}
