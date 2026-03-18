package com.example.be12th.domain.favorite.presentation;

import com.example.be12th.domain.favorite.service.FavoriteClubDeleteService;
import com.example.be12th.domain.favorite.service.FavoriteClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favorite")
public class FavoriteController {
    private final FavoriteClubService favoriteClubService;
    private final FavoriteClubDeleteService favoriteClubDeleteService;

    //club
    @PostMapping("/club/{clubId}")
    @ResponseStatus(HttpStatus.OK)
    public void favoriteClub(@PathVariable Long clubId) {
        favoriteClubService.execute(clubId);
    }
    @DeleteMapping("/club/{clubId}")
    @ResponseStatus(HttpStatus.OK)
    public void unfavoriteClub(@PathVariable Long clubId) {
        favoriteClubDeleteService.execute(clubId);
    }
    //player
    @PostMapping("/player/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public void favoritePlayer(@PathVariable Long playerId) {
        favoriteClubService.execute(playerId);
    }
    
}
