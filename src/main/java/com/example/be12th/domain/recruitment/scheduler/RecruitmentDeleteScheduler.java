package com.example.be12th.domain.recruitment.scheduler;

import com.example.be12th.domain.recruitment.domain.repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
@RequiredArgsConstructor
public class RecruitmentDeleteScheduler {

    private final RecruitmentRepository recruitmentRepository;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void deleteRecruitments() {
        recruitmentRepository.deleteByExpiredAtBefore(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
    }
}
