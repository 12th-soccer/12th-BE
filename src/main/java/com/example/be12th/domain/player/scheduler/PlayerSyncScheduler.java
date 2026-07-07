package com.example.be12th.domain.player.scheduler;

import com.example.be12th.domain.footballapi.support.KLeagueConstants;
import com.example.be12th.domain.player.service.PlayerSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "player.sync.enabled", havingValue = "true", matchIfMissing = true)
public class PlayerSyncScheduler {

    private static final ZoneId SEOUL_ZONE = ZoneId.of("Asia/Seoul");

    private final PlayerSyncService playerSyncService;

    @Scheduled(cron = "${player.sync.cron:0 0 1 * * *}", zone = "Asia/Seoul")
    public void syncDailyPlayers() {
        int season = LocalDate.now(SEOUL_ZONE).getYear();

        playerSyncService.executeAll(KLeagueConstants.KLEAGUE1_ID, season);
        playerSyncService.executeAll(KLeagueConstants.KLEAGUE2_ID, season);
    }
}
