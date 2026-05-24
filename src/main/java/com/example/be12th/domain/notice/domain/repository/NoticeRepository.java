package com.example.be12th.domain.notice.domain.repository;

import com.example.be12th.domain.notice.domain.Notice;
import com.example.be12th.domain.recruitment.domain.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Optional<Notice> findByRecruitment(Recruitment recruitment);
}
