package com.example.be12th.domain.fcm.schedule;

import com.example.be12th.domain.fcm.service.MatchNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "firebase.enabled", havingValue = "true")
public class MatchNotificationScheduler {

    private final MatchNotificationService matchNotificationService;

    @Scheduled(fixedRate = 60000)
    public void sendMatchReminder() {
        matchNotificationService.sendUpcomingMatchNotifications();
    }
}
