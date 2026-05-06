package com.example.be12th.domain.favorite.presnetation;

import com.example.be12th.domain.favorite.presnetation.dto.response.FavoriteTeamResponse;
import com.example.be12th.domain.favorite.service.FavoriteTeamDeleteService;
import com.example.be12th.domain.favorite.service.FavoriteTeamQueryService;
import com.example.be12th.domain.favorite.service.FavoriteTeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favorite")
public class FavoriteTeamController {
    private final FavoriteTeamService favoriteTeamService;
    private final FavoriteTeamDeleteService favoriteTeamDeleteService;
    private final FavoriteTeamQueryService favoriteTeamQueryService;

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
}
