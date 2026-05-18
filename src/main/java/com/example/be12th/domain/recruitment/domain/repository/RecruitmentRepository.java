package com.example.be12th.domain.recruitment.domain.repository;

import com.example.be12th.domain.recruitment.domain.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {
    void deleteByExpiredAtBefore(LocalDateTime now);
}
