package com.example.be12th.domain.team.scheduler;

import com.example.be12th.domain.footballapi.support.KLeagueConstants;
import com.example.be12th.domain.team.service.TeamSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "team.sync.enabled", havingValue = "true", matchIfMissing = true)
public class TeamSyncScheduler {

    private static final ZoneId SEOUL_ZONE = ZoneId.of("Asia/Seoul");

    private final TeamSyncService teamSyncService;

    @Scheduled(cron = "${team.sync.cron:0 30 0 * * *}", zone = "Asia/Seoul")
    public void syncDailyTeams() {
        int season = LocalDate.now(SEOUL_ZONE).getYear();

        teamSyncService.execute(KLeagueConstants.KLEAGUE1_ID, season);
        teamSyncService.execute(KLeagueConstants.KLEAGUE2_ID, season);
    }
}
