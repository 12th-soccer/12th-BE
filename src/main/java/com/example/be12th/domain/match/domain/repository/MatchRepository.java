package com.example.be12th.domain.match.domain.repository;

import com.example.be12th.domain.match.domain.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByHomeClubIdOrAwayClubId(Long homeClubId, Long awayClubId);
    List<Match> findByMatchDateBetween(LocalDateTime start, LocalDateTime end);
    @Query("""
    select m
    from Match m
    join fetch m.homeClub
    join fetch m.awayClub
    where m.matchDate between :start and :end
""")
    List<Match> findMatchesStartingBetween(LocalDateTime start, LocalDateTime end);
}
