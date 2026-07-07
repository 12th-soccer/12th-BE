package com.example.be12th.domain.match.domain.repository;

import com.example.be12th.domain.match.domain.FootballMatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FootballMatchRepository extends JpaRepository<FootballMatch, Long> {

    Optional<FootballMatch> findByExternalFixtureId(Long externalFixtureId);

    List<FootballMatch> findAllByLeagueIdAndSeasonAndMatchDateStartingWithOrderByMatchDateAsc(
            Long leagueId,
            Integer season,
            String matchDate
    );
}
