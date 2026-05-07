package com.example.be12th.domain.goal.service;

import com.example.be12th.domain.footballapi.client.FootballClient;
import com.example.be12th.domain.footballapi.presentation.dto.external.*;
import com.example.be12th.domain.footballapi.support.KLeagueConstants;
import com.example.be12th.domain.goal.presentation.dto.PlayerGoalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalQueryService {
    private final FootballClient footballClient;

    public PlayerGoalResponse execute(Long playerId, int season, String league){
        PlayerApiResponse result = footballClient.getPlayerDetail(playerId, season);

        if(result == null || result.response() == null || result.response().isEmpty()){
            return null;
        }

        PlayerItem item = result.response().get(0);
        Long leagueId = KLeagueConstants.resolveLeagueId(league);

        PlayerStatisticItem statistic = pickStatistic(item.statistics(), leagueId);
        if (statistic == null) {
            throw new RuntimeException("해당 리그 통계를 찾을 수 없습니다.");
        }

        if (statistic.goals() == null) {
            throw new RuntimeException("선수 goals 통계가 없습니다.");
        }

        return new PlayerGoalResponse(
                item.player().id(),
                item.player().name(),
                season,
                league,
                statistic.goals().total() == null
                        ? 0
                        : statistic.goals().total()
        );
    }

    private PlayerStatisticItem pickStatistic(List<PlayerStatisticItem> statistics, Long leagueId) {
        if (statistics == null || statistics.isEmpty()) {
            return null;
        }

        return statistics.stream()
                .filter(stat -> stat.league() != null)
                .filter(stat -> stat.league().id() != null)
                .filter(stat -> stat.league().id().equals(leagueId))
                .findFirst()
                .orElse(null);
    }
}
