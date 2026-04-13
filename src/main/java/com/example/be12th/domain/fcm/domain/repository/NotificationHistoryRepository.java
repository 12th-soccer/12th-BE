package com.example.be12th.domain.fcm.domain.repository;

import com.example.be12th.domain.fcm.domain.NotificationHistory;
import com.example.be12th.domain.fcm.domain.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationHistoryRepository extends JpaRepository<NotificationHistory, Long> {
    boolean existsByUserIdAndMatchIdAndNotificationType(Long userId, Long matchId, NotificationType notificationType);
}