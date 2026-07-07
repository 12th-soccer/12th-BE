package com.example.be12th.domain.match.scheduler;

import com.example.be12th.domain.footballapi.support.KLeagueConstants;
import com.example.be12th.domain.match.service.MatchSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "match.sync.enabled", havingValue = "true", matchIfMissing = true)
public class MatchSyncScheduler {

    private static final ZoneId SEOUL_ZONE = ZoneId.of("Asia/Seoul");

    private final MatchSyncService matchSyncService;

    @Scheduled(cron = "${match.sync.cron:0 0 0 * * *}", zone = "Asia/Seoul")
    public void syncDailyMatches() {
        int season = LocalDate.now(SEOUL_ZONE).getYear();

        matchSyncService.execute(KLeagueConstants.KLEAGUE1_ID, season);
        matchSyncService.execute(KLeagueConstants.KLEAGUE2_ID, season);
    }
}
