package com.example.be12th.domain.goal.domain.repository;

import com.example.be12th.domain.goal.domain.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    @Query("SELECT g FROM Goal g JOIN FETCH g.match WHERE g.player.id = :playerId")
    List<Goal> findByPlayerId(Long playerId);
}
