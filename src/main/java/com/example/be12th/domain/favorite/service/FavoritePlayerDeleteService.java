package com.example.be12th.domain.favorite.service;

import com.example.be12th.domain.favorite.domain.FavoritePlayer;
import com.example.be12th.domain.favorite.domain.Repository.FavoritePlayerRepository;
import com.example.be12th.domain.user.facade.UserFacade;
import com.example.be12th.global.error.exception.App12thException;
import com.example.be12th.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoritePlayerDeleteService {
    private final FavoritePlayerRepository favoritePlayerRepository;
    private final UserFacade userFacade;

    @Transactional
    public void execute(Long playerId) {
        Long userId = userFacade.currentUserId();

        FavoritePlayer favoritePlayer = favoritePlayerRepository.findByUserIdAndPlayerId(userId, playerId)
                .orElseThrow(() -> new App12thException(ErrorCode.FAVORITE_PLAYER_NOT_FOUND));

        favoritePlayerRepository.deleteById(favoritePlayer.getId());

    }
}
