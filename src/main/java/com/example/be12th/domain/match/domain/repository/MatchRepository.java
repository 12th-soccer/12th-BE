package com.example.be12th.domain.match.domain.repository;

import com.example.be12th.domain.match.domain.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
}
