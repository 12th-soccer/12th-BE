package com.example.be12th.domain.favorite.presentation;

import com.example.be12th.domain.favorite.presentation.dto.response.FavoritePlayerResponse;
import com.example.be12th.domain.favorite.presentation.dto.response.FavoriteTeamResponse;
import com.example.be12th.domain.favorite.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favorite")
public class FavoriteTeamController {
    //team
    private final FavoriteTeamService favoriteTeamService;
    private final FavoriteTeamDeleteService favoriteTeamDeleteService;
    private final FavoriteTeamQueryService favoriteTeamQueryService;

    //player
    private final FavoritePlayerService favoritePlayerService;
    private final FavoritePlayerDeleteService favoritePlayerDeleteService;
    private final FavoritePlayerQueryService favoritePlayerQueryService;

    @PostMapping("/team/{teamId}")
    public void favoriteTeam(@PathVariable Long teamId) {
        favoriteTeamService.execute(teamId);
    }

    @DeleteMapping("/team/{teamId}")
    public void deleteFavoriteTeam(@PathVariable Long teamId) {
        favoriteTeamDeleteService.execute(teamId);
    }

    @GetMapping("/team")
    public List<FavoriteTeamResponse> getFavoriteTeams() {
        return favoriteTeamQueryService.execute();
    }

    @PostMapping("/player/{playerId}")
    public void favoritePlayer(
            @PathVariable Long playerId,
            @RequestParam Long leagueId,
            @RequestParam int season
    ) {
        favoritePlayerService.execute(playerId, leagueId, season);
    }

    @DeleteMapping("/player/{playerId}")
    public void deleteFavoritePlayer(@PathVariable Long playerId) {
        favoritePlayerDeleteService.execute(playerId);
    }

    @GetMapping("/player")
    public List<FavoritePlayerResponse> getFavoritePlayers() {
        return favoritePlayerQueryService.execute();
    }
}
