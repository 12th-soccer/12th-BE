package com.example.be12th.domain.goal.domain.repository;

import com.example.be12th.domain.goal.domain.GoalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<GoalEntity, Long> {
    
}
