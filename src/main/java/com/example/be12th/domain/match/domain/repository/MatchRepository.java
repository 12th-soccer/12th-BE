package com.example.be12th.domain.match.domain.repository;

import com.example.be12th.domain.league.LeagueType;
import com.example.be12th.domain.match.domain.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByHomeClubIdOrAwayClubId(Long homeClubId, Long awayClubId);
    List<Match> findByMatchDateBetween(LocalDateTime start, LocalDateTime end);
    List<Match> findByLeagueTypeOrderByMatchDateAsc(LeagueType leagueType);
}
