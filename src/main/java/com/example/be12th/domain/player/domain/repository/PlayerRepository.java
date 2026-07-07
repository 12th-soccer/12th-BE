package com.example.be12th.domain.player.domain.repository;

import com.example.be12th.domain.player.domain.Player;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findByExternalPlayerIdAndLeagueIdAndSeasonAndTeamId(
            Long externalPlayerId,
            Long leagueId,
            Integer season,
            Long teamId
    );

    List<Player> findAllByLeagueIdAndSeason(
            Long leagueId,
            Integer season
    );

    List<Player> findAllByLeagueIdAndSeason(
            Long leagueId,
            Integer season,
            Pageable pageable
    );

    List<Player> findAllByLeagueIdAndSeasonAndNameContainingIgnoreCase(
            Long leagueId,
            Integer season,
            String name
    );

    List<Player> findAllByLeagueIdAndSeasonAndNameContainingIgnoreCase(
            Long leagueId,
            Integer season,
            String name,
            Pageable pageable
    );

    Optional<Player> findFirstByExternalPlayerIdAndSeasonOrderByIdDesc(
            Long externalPlayerId,
            Integer season
    );
}
