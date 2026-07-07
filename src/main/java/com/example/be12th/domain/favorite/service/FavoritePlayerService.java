package com.example.be12th.domain.favorite.service;

import com.example.be12th.domain.favorite.domain.FavoritePlayer;
import com.example.be12th.domain.favorite.domain.Repository.FavoritePlayerRepository;
import com.example.be12th.domain.player.presentation.dto.response.PlayerResponse;
import com.example.be12th.domain.player.service.PlayerQueryService;
import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.facade.UserFacade;
import com.example.be12th.global.error.exception.App12thException;
import com.example.be12th.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoritePlayerService {
    private final FavoritePlayerRepository favoritePlayerRepository;
    private final UserRepository userRepository;
    private final UserFacade userFacade;
    private final PlayerQueryService playerQueryService;

    @Transactional
    public void execute(Long playerId, Long leagueId, int season) {
        Long userId = userFacade.currentUserId();

        if (favoritePlayerRepository.existsByUserIdAndPlayerId(userId, playerId)) {
            throw new App12thException(ErrorCode.FAVORITE_PLAYER_ALREADY_EXISTS);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new App12thException(ErrorCode.USER_NOT_FOUND));

        PlayerResponse player = playerQueryService.execute(playerId, leagueId, season);
        if (player == null) {
            throw new App12thException(ErrorCode.PLAYER_NOT_FOUND);
        }

        favoritePlayerRepository.save(
                FavoritePlayer.builder()
                        .user(user)
                        .playerId(player.playerId())
                        .playerName(player.name())
                        .playerLogo(player.photo())
                        .build()
        );
    }
}
