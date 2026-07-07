package com.example.be12th.domain.team.domain.repository;

import com.example.be12th.domain.team.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByExternalTeamIdAndLeagueIdAndSeason(
            Long externalTeamId,
            Long leagueId,
            Integer season
    );

    List<Team> findAllByLeagueIdAndSeason(
            Long leagueId,
            Integer season
    );
}

