package com.example.be12th.domain.matchevent.domain.repository;

import com.example.be12th.domain.matchevent.domain.MatchEvent;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MatchEventRepository extends JpaRepository<MatchEvent, Long> {
    @Query("select me from MatchEvent me join fetch me.player where me.match.id = :matchId order by me.eventMinute asc, me.id asc")
    List<MatchEvent> findByMatchId(@Param("matchId") Long matchId);
}
