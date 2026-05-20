package com.example.be12th.domain.recruitment.domain.repository;

import com.example.be12th.domain.recruitment.domain.Recruitment;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {
    void deleteByExpiredAtBefore(LocalDateTime now);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select r from Recruitment r where r.recruitmentId = :id")
    Optional<Recruitment> findByIdForUpdate(@Param("id") Long id);
}
