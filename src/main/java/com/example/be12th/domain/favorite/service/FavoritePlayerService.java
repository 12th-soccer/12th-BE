package com.example.be12th.domain.favorite.service;

import com.example.be12th.domain.favorite.domain.FavoritePlayer;
import com.example.be12th.domain.favorite.domain.repository.FavoritePlayerRepository;
import com.example.be12th.domain.player.domain.Player;
import com.example.be12th.domain.player.domain.repository.PlayerRepository;
import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoritePlayerService {
    private final FavoritePlayerRepository favoritePlayerRepository;
    private final UserRepository userRepository;
    private final PlayerRepository playerRepository;
    private final UserFacade userFacade;

    public void execute(Long playerId) {
        User user = userRepository.findById(userFacade.currentUserId())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("해당 선수를 찾을 수 없습니다."));

        final Boolean existUserAndPlayer = favoritePlayerRepository.existsByUserAndPlayer(user,player);
        if (existUserAndPlayer) {
            throw new RuntimeException("이미 즐겨찾기된 선수입니다.");
        }
        FavoritePlayer favoritePlayer = FavoritePlayer.builder()
                .user(user)
                .player(player)
                .build();
        favoritePlayerRepository.save(favoritePlayer);
    }
}
