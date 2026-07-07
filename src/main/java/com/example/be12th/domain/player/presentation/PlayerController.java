package com.example.be12th.domain.player.presentation;

import com.example.be12th.domain.player.presentation.dto.response.PlayerResponse;
import com.example.be12th.domain.player.service.PlayerQueryService;
import com.example.be12th.domain.player.service.PlayerSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/player")
public class PlayerController {
    private final PlayerQueryService playerQueryService;
    private final PlayerSyncService playerSyncService;

    @PostMapping("/sync")
    public void syncPlayers(
            @RequestParam Long leagueId,
            @RequestParam int season,
            @RequestParam(required = false) Integer page
    ) {
        if (page == null) {
            playerSyncService.executeAll(leagueId, season);
            return;
        }

        playerSyncService.execute(leagueId, season, page);
    }

    @GetMapping("/{playerId}")
    public PlayerResponse getPlayerById(@PathVariable Long playerId, @RequestParam int season) {
        return playerQueryService.execute(playerId, season);
    }

    @GetMapping("/kleague1")
    public List<PlayerResponse> getKLeague1Players(@RequestParam int season, @RequestParam(defaultValue = "1") int page) {
        return playerQueryService.getKLeague1Players(season, page);
    }

    @GetMapping("/kleague2")
    public List<PlayerResponse> getKLeague2Players(@RequestParam int season, @RequestParam(defaultValue = "1") int page) {
        return playerQueryService.getKLeague2Players(season, page);
    }

    @GetMapping("/search")
    public List<PlayerResponse> searchPlayers(
            @RequestParam String keyword,
            @RequestParam int season,
            @RequestParam(defaultValue = "1") int page
    ) {
        return playerQueryService.searchPlayers(keyword, season, page);
    }

}
