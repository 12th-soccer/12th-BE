package com.example.be12th.domain.favorite.domain.Repository;

import com.example.be12th.domain.favorite.domain.FavoritePlayer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoritePlayerRepository extends JpaRepository<FavoritePlayer, Long> {
    boolean existsByUserIdAndPlayerId(Long userId, Long playerId);
    Optional<FavoritePlayer> findByUserIdAndPlayerId(Long userId, Long playerId);
    List<FavoritePlayer> findAllByUserId(Long userId);
}
