package com.example.be12th.domain.fcm.schedule;

import com.example.be12th.domain.fcm.service.MatchNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchNotificationScheduler {

    private final MatchNotificationService matchNotificationService;

    @Scheduled(fixedRate = 60000)
    public void sendMatchReminder() {
        matchNotificationService.sendUpcomingMatchNotifications();
    }
}
