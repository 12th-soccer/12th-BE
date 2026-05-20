package com.example.be12th.domain.notice.domain.repository;

import com.example.be12th.domain.notice.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
