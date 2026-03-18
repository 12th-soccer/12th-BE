package com.example.be12th.domain.favorite.domain.repository;

import com.example.be12th.domain.favorite.domain.FavoritePlayer;
import com.example.be12th.domain.player.domain.Player;
import com.example.be12th.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoritePlayerRepository extends JpaRepository<FavoritePlayer, Long> {
    List<FavoritePlayer> findByUser(User user);
    Optional<FavoritePlayer> findByUserIdAndPlayerId(Long userId, Long playerId);
    Boolean existsByUserAndPlayer(User user, Player player);
}
