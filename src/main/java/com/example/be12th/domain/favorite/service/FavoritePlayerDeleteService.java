package com.example.be12th.domain.favorite.service;

import com.example.be12th.domain.favorite.domain.FavoritePlayer;
import com.example.be12th.domain.favorite.domain.Repository.FavoritePlayerRepository;
import com.example.be12th.domain.user.facade.UserFacade;
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
                .orElseThrow(() -> new RuntimeException("해당 즐겨찾기한 선수를 찾을수없습니다."));

        favoritePlayerRepository.deleteById(favoritePlayer.getId());

    }
}
