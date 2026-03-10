package com.example.be12th.domain.player.presentation;

import com.example.be12th.domain.player.presentation.dto.response.PlayerResponse;
import com.example.be12th.domain.player.presentation.dto.response.PlayerSearchResponse;
import com.example.be12th.domain.player.service.PlayerQueryService;
import com.example.be12th.domain.player.service.PlayerSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/player")
public class PlayerController {
    private final PlayerQueryService playerQueryService;
    private final PlayerSearchService playerSearchService;

    @GetMapping("/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public PlayerResponse query(@PathVariable Long playerId){
        return playerQueryService.execute(playerId);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Page<PlayerSearchResponse> search(@RequestParam String keyword, Pageable pageable){
        return playerSearchService.execute(keyword,pageable);
    }
}
