package com.example.be12th.domain.favorite.domain.Repository;

import com.example.be12th.domain.favorite.domain.FavoriteTeam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteTeamRepository extends JpaRepository<FavoriteTeam, Long> {
    boolean existsByUserIdAndTeamId(Long userId, Long teamId);
    Optional<FavoriteTeam> findByUserIdAndTeamId(Long userId, Long teamId);
    List<FavoriteTeam> findAllByUserId(Long userId);
}
