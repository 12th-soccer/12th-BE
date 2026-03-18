package com.example.be12th.domain.favorite.service;

import com.example.be12th.domain.favorite.domain.FavoritePlayer;
import com.example.be12th.domain.favorite.domain.repository.FavoritePlayerRepository;
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
        FavoritePlayer favoritePlayer = favoritePlayerRepository.findByUserIdAndPlayerId(userFacade.currentUserId(), playerId)
                .orElseThrow(() -> new RuntimeException("해당 관심 선수를 찾을 수 없습니다."));
        favoritePlayerRepository.delete(favoritePlayer);
    }
}
