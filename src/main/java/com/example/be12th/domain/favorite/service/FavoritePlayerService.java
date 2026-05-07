package com.example.be12th.domain.favorite.service;

import com.example.be12th.domain.favorite.domain.FavoritePlayer;
import com.example.be12th.domain.favorite.domain.Repository.FavoritePlayerRepository;
import com.example.be12th.domain.player.presentation.dto.response.PlayerResponse;
import com.example.be12th.domain.player.service.PlayerQueryService;
import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.facade.UserFacade;
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
    public void execute(Long playerId, int season) {
        Long userId = userFacade.currentUserId();

        if (favoritePlayerRepository.existsByUserIdAndPlayerId(userId, playerId)) {
            throw new RuntimeException("이미 즐겨찾기한 선수입니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저를 찾을 수 없습니다."));

        PlayerResponse player = playerQueryService.execute(playerId, season);
        if (player == null) {
            throw new RuntimeException("해당 선수를 찾을 수 없습니다.");
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
