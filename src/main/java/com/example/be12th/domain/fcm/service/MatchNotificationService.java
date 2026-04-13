package com.example.be12th.domain.fcm.service;

import com.example.be12th.domain.favorite.domain.repository.FavoriteClubRepository;
import com.example.be12th.domain.fcm.domain.NotificationType;
import com.example.be12th.domain.fcm.domain.repository.FcmRepository;
import com.example.be12th.domain.fcm.domain.repository.NotificationHistoryRepository;
import com.example.be12th.domain.fcm.domain.repository.NotificationSettingRepository;
import com.example.be12th.domain.match.domain.Match;
import com.example.be12th.domain.match.domain.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchNotificationService {
    private final MatchRepository matchRepository;
    private final FavoriteClubRepository favoriteClubRepository;
    private final FcmRepository fcmRepository;
    private final FcmService fcmService;
    private final NotificationHistoryRepository notificationHistoryRepository;
    private final NotificationSettingRepository notificationSettingRepository;

    public void sendUpcomingMatchNotifications() {
        sendNotificationsByType(60, NotificationType.MATCH_REMINDER_1H);
        sendNotificationsByType(30, NotificationType.MATCH_REMINDER_30M);
        sendNotificationsByType(15, NotificationType.MATCH_REMINDER_15M);
        sendNotificationsByType(0, NotificationType.MATCH_START);
    }

    private void sendNotificationsByType(int minutesBefore, NotificationType notificationType) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime targetStart = now.plusMinutes(minutesBefore);

        List<Match> matches = matchRepository.findMatchesStartingBetween(
                targetStart.minusMinutes(1),
                targetStart.plusMinutes(1)
        );

        for (Match match : matches) {
            sendMatchReminder(match, notificationType);
        }
    }

    private void sendMatchReminder(Match match, NotificationType notificationType) {
        String title = getTitle(notificationType);
        String body = getBody(match, notificationType);

        Long homeClubId = match.getHomeClub().getId();
        Long awayClubId = match.getAwayClub().getId();

        List<Long> userIds = favoriteClubRepository.findUserIdsByClubIds(homeClubId, awayClubId);

        for (Long userId : userIds) {
            boolean alreadySent = notificationHistoryRepository
                    .existsByUserIdAndMatchIdAndNotificationType(userId, match.getId(), notificationType);

            if (alreadySent) {
                continue;
            }

            boolean enabled = isNotificationEnabled(userId, notificationType);
            if (!enabled) {
                continue;
            }

            List<String> tokens = fcmRepository.findTokensByUserId(userId);

            for (String token : tokens) {
                fcmService.sendMessage(token, title, body);
            }

            notificationHistoryRepository.save(
                    com.example.be12th.domain.fcm.domain.NotificationHistory.builder()
                            .userId(userId)
                            .matchId(match.getId())
                            .notificationType(notificationType)
                            .build()
            );
        }
    }

    private boolean isNotificationEnabled(Long userId, NotificationType type) {
        return notificationSettingRepository.findByUserId(userId)
                .map(setting -> {
                    if (!setting.getNotificationEnabled()) return false;

                    return switch (type) {
                        case MATCH_REMINDER_1H -> setting.getOneHourBeforeEnabled();
                        case MATCH_REMINDER_30M -> setting.getThirtyMinutesBeforeEnabled();
                        case MATCH_REMINDER_15M -> setting.getFifteenMinutesBeforeEnabled();
                        case MATCH_START -> setting.getMatchStartEnabled();
                    };
                })
                .orElse(false);
    }

    private String getTitle(NotificationType type) {
        return switch (type) {
            case MATCH_REMINDER_1H -> "경기 1시간 전 알림";
            case MATCH_REMINDER_30M -> "경기 30분 전 알림";
            case MATCH_REMINDER_15M -> "경기 15분 전 알림";
            case MATCH_START -> "경기 시작 알림";
        };
    }

    private String getBody(Match match, NotificationType type) {
        String matchInfo = match.getHomeClub().getClubName() + " vs " + match.getAwayClub().getClubName();

        return switch (type) {
            case MATCH_REMINDER_1H -> matchInfo + " 경기가 1시간 뒤 시작합니다.";
            case MATCH_REMINDER_30M -> matchInfo + " 경기가 30분 뒤 시작합니다.";
            case MATCH_REMINDER_15M -> matchInfo + " 경기가 15분 뒤 시작합니다.";
            case MATCH_START -> matchInfo + " 경기가 지금 시작합니다.";
        };
    }
}