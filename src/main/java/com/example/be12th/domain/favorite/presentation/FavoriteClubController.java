package com.example.be12th.domain.favorite.presentation;

import com.example.be12th.domain.favorite.service.FavoriteClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favorite")
public class FavoriteClubController {
    private final FavoriteClubService favoriteClubService;

    @PostMapping("/club/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void favoriteClub(@PathVariable Long id) {
        favoriteClubService.execute(id);
    }


}
