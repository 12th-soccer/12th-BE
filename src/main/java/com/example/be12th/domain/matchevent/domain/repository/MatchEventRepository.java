package com.example.be12th.domain.matchevent.domain.repository;

import com.example.be12th.domain.match.domain.Match;
import com.example.be12th.domain.matchevent.domain.MatchEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchEventRepository extends JpaRepository<MatchEvent, Long> {
    List<MatchEvent> findByMatchId(Long matchId);

    Long match(Match match);
}
