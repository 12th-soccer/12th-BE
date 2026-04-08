package com.example.be12th.domain.league.domain.repository;

import com.example.be12th.domain.league.domain.League;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueRepository extends JpaRepository<League, Long> {
}
