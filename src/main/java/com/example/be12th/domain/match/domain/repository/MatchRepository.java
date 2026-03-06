package com.example.be12th.domain.match.domain.repository;

import com.example.be12th.domain.match.domain.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByHomeClub_IdOrAwayClub_Id(Long homeClubId, Long awayClubId);
}
