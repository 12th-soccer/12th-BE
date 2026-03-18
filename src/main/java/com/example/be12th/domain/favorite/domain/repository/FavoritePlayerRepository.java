package com.example.be12th.domain.favorite.domain.repository;

import com.example.be12th.domain.favorite.domain.FavoritePlayer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoritePlayerRepository extends JpaRepository<FavoritePlayer, Long> {
}
