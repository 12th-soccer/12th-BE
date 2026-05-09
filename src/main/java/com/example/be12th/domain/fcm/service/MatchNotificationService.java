package com.example.be12th.domain.fcm.service;

import com.example.be12th.domain.favorite.domain.FavoriteTeam;
import com.example.be12th.domain.favorite.domain.Repository.FavoriteTeamRepository;
import com.example.be12th.domain.fcm.domain.NotificationHistory;
import com.example.be12th.domain.fcm.domain.NotificationSetting;
import com.example.be12th.domain.fcm.domain.NotificationType;
import com.example.be12th.domain.fcm.domain.repository.FcmRepository;
import com.example.be12th.domain.fcm.domain.repository.NotificationHistoryRepository;
import com.example.be12th.domain.fcm.domain.repository.NotificationSettingRepository;
import com.example.be12th.domain.footballapi.client.FootballClient;
import com.example.be12th.domain.footballapi.dto.external.FixtureItem;
import com.example.be12th.domain.footballapi.support.KLeagueConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "firebase.enabled", havingValue = "true")
public class MatchNotificationService {

    private static final ZoneId KOREA_ZONE = ZoneId.of("Asia/Seoul");
    private static final DateTimeFormatter MATCH_TIME_FORMATTER = DateTimeFormatter.ofPattern("M월 d일 HH:mm");

    private final FootballClient footballClient;
    private final FavoriteTeamRepository favoriteTeamRepository;
    private final NotificationSettingRepository notificationSettingRepository;
    private final NotificationHistoryRepository notificationHistoryRepository;
    private final FcmRepository fcmRepository;
    private final FcmService fcmService;

    @Transactional
    public void sendUpcomingMatchNotifications() {
        LocalDate today = LocalDate.now(KOREA_ZONE);
        LocalDate tomorrow = today.plusDays(1);
        int season = today.getYear();

        List<FixtureItem> fixtures = List.of(
                footballClient.getFixturesByRange(KLeagueConstants.KLEAGUE1_ID, season, today.toString(), tomorrow.toString()),
                footballClient.getFixturesByRange(KLeagueConstants.KLEAGUE2_ID, season, today.toString(), tomorrow.toString())
        ).stream()
                .filter(response -> response != null && response.response() != null)
                .flatMap(response -> response.response().stream())
                .toList();

        for (FixtureItem fixture : fixtures) {
            Optional<NotificationType> notificationType = resolveNotificationType(fixture);
            if (notificationType.isEmpty()) {
                continue;
            }

            Map<Long, FavoriteTeam> favoriteUsers = new LinkedHashMap<>();
            collectFavoriteUsers(favoriteUsers, fixture.teams().home().id());
            collectFavoriteUsers(favoriteUsers, fixture.teams().away().id());

            for (FavoriteTeam favoriteTeam : favoriteUsers.values()) {
                sendNotificationIfNeeded(favoriteTeam, fixture, notificationType.get());
            }
        }
    }

    private void collectFavoriteUsers(Map<Long, FavoriteTeam> favoriteUsers, Long teamId) {
        if (teamId == null) {
            return;
        }

        for (FavoriteTeam favoriteTeam : favoriteTeamRepository.findAllByTeamId(teamId)) {
            favoriteUsers.putIfAbsent(favoriteTeam.getUser().getId(), favoriteTeam);
        }
    }

    private Optional<NotificationType> resolveNotificationType(FixtureItem fixture) {
        if (fixture == null || fixture.fixture() == null || fixture.fixture().date() == null) {
            return Optional.empty();
        }

        long secondsUntilStart = Duration.between(
                OffsetDateTime.now(),
                OffsetDateTime.parse(fixture.fixture().date())
        ).getSeconds();

        if (isWithinWindow(secondsUntilStart, 60)) {
            return Optional.of(NotificationType.MATCH_REMINDER_1H);
        }
        if (isWithinWindow(secondsUntilStart, 30)) {
            return Optional.of(NotificationType.MATCH_REMINDER_30M);
        }
        if (isWithinWindow(secondsUntilStart, 15)) {
            return Optional.of(NotificationType.MATCH_REMINDER_15M);
        }
        if (secondsUntilStart >= 0 && secondsUntilStart < 60) {
            return Optional.of(NotificationType.MATCH_START);
        }

        return Optional.empty();
    }

    private boolean isWithinWindow(long secondsUntilStart, int targetMinutes) {
        long targetSeconds = targetMinutes * 60L;
        return secondsUntilStart >= targetSeconds - 60 && secondsUntilStart < targetSeconds;
    }

    private void sendNotificationIfNeeded(FavoriteTeam favoriteTeam, FixtureItem fixture, NotificationType notificationType) {
        Long userId = favoriteTeam.getUser().getId();

        NotificationSetting setting = notificationSettingRepository.findByUserId(userId)
                .orElseGet(() -> notificationSettingRepository.save(
                        NotificationSetting.builder()
                                .user(favoriteTeam.getUser())
                                .notificationEnabled(true)
                                .oneHourBeforeEnabled(true)
                                .thirtyMinutesBeforeEnabled(true)
                                .fifteenMinutesBeforeEnabled(true)
                                .matchStartEnabled(true)
                                .favoriteTeamMatchEnabled(true)
                                .build()
                ));

        if (!isEnabled(setting, notificationType)) {
            return;
        }

        Long matchId = fixture.fixture().id();
        if (matchId == null || notificationHistoryRepository.existsByUserIdAndMatchIdAndNotificationType(userId, matchId, notificationType)) {
            return;
        }

        List<String> tokens = fcmRepository.findTokensByUserId(userId);
        if (tokens.isEmpty()) {
            return;
        }

        String title = buildTitle(notificationType);
        String body = buildBody(favoriteTeam, fixture, notificationType);

        for (String token : tokens) {
            fcmService.sendMessage(token, title, body);
        }

        notificationHistoryRepository.save(NotificationHistory.builder()
                .userId(userId)
                .matchId(matchId)
                .notificationType(notificationType)
                .build());
    }

    private boolean isEnabled(NotificationSetting setting, NotificationType notificationType) {
        if (!Boolean.TRUE.equals(setting.getNotificationEnabled()) || !Boolean.TRUE.equals(setting.getFavoriteTeamMatchEnabled())) {
            return false;
        }

        return switch (notificationType) {
            case MATCH_REMINDER_1H -> Boolean.TRUE.equals(setting.getOneHourBeforeEnabled());
            case MATCH_REMINDER_30M -> Boolean.TRUE.equals(setting.getThirtyMinutesBeforeEnabled());
            case MATCH_REMINDER_15M -> Boolean.TRUE.equals(setting.getFifteenMinutesBeforeEnabled());
            case MATCH_START -> Boolean.TRUE.equals(setting.getMatchStartEnabled());
        };
    }

    private String buildTitle(NotificationType notificationType) {
        return switch (notificationType) {
            case MATCH_REMINDER_1H -> "관심구단 경기 1시간 전";
            case MATCH_REMINDER_30M -> "관심구단 경기 30분 전";
            case MATCH_REMINDER_15M -> "관심구단 경기 15분 전";
            case MATCH_START -> "관심구단 경기 시작";
        };
    }

    private String buildBody(FavoriteTeam favoriteTeam, FixtureItem fixture, NotificationType notificationType) {
        OffsetDateTime kickoff = OffsetDateTime.parse(fixture.fixture().date());
        String homeTeam = fixture.teams().home().name();
        String awayTeam = fixture.teams().away().name();
        String kickoffText = kickoff.atZoneSameInstant(KOREA_ZONE).format(MATCH_TIME_FORMATTER);

        if (notificationType == NotificationType.MATCH_START) {
            return favoriteTeam.getTeamName() + "의 경기가 시작됐습니다. " + homeTeam + " vs " + awayTeam;
        }

        return favoriteTeam.getTeamName() + " 경기 알림: " + kickoffText + " " + homeTeam + " vs " + awayTeam;
    }
}
