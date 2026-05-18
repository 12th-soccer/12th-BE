package com.example.be12th.domain.spoiler.domain.repository;

import com.example.be12th.domain.spoiler.domain.SpoilerSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpoilerRepository extends JpaRepository<SpoilerSetting, Long> {
    Optional<SpoilerSetting> findById(Long id);
}
