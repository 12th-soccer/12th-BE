package com.example.be12th.domain.player.service;

import com.example.be12th.domain.footballapi.dto.external.PlayerApiResponse;
import com.example.be12th.domain.footballapi.dto.external.PlayerItem;
import com.example.be12th.domain.footballapi.dto.external.PlayerSquadApiResponse;
import com.example.be12th.domain.footballapi.dto.external.PlayerSquadInfo;
import com.example.be12th.domain.footballapi.dto.external.PlayerSquadItem;
import com.example.be12th.domain.footballapi.dto.external.PlayerStatisticItem;
import com.example.be12th.domain.player.presentation.dto.response.PlayerResponse;
import com.example.be12th.domain.footballapi.client.FootballClient;
import com.example.be12th.domain.footballapi.support.KLeagueConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerQueryService {
    private final FootballClient footballClient;

    public PlayerResponse execute(Long playerId, int season) {
        PlayerApiResponse result = footballClient.getPlayerDetail(playerId, season);

        if (result == null || result.response() == null || result.response().isEmpty()) {
            return null;
        }

        return toPlayerResponse(result.response().get(0), true);
    }

    public List<PlayerResponse> getKLeague1Players(int season, int page) {
        return getPlayersByLeague(KLeagueConstants.KLEAGUE1_ID, season, page);
    }

    public List<PlayerResponse> getKLeague2Players(int season, int page) {
        return getPlayersByLeague(KLeagueConstants.KLEAGUE2_ID, season, page);
    }


    private List<PlayerResponse> getPlayersByLeague(Long leagueId, int season, int page) {
        PlayerApiResponse result = footballClient.getPlayersByLeague(leagueId, season, page);

        if (result == null || result.response() == null) {
            return List.of();
        }

        return result.response().stream()
                .map(item -> toPlayerResponse(item, false))
                .filter(player -> player != null && player.teamId() != null)
                .toList();
    }

    private PlayerResponse toPlayerResponse(PlayerItem item, boolean includeSquadInfo) {
        if (item == null || item.player() == null) {
            return null;
        }

        PlayerStatisticItem statistic = pickStatistic(item.statistics());
        Long teamId = statistic == null || statistic.team() == null ? null : statistic.team().id();
        PlayerSquadInfo squadInfo = includeSquadInfo ? findSquadInfo(teamId, item.player().id()) : null;
        Integer number = squadInfo != null && squadInfo.number() != null
                ? squadInfo.number()
                : statistic == null || statistic.games() == null ? null : statistic.games().number();
        String position = squadInfo != null && squadInfo.position() != null
                ? squadInfo.position()
                : statistic == null || statistic.games() == null ? null : statistic.games().position();

        return new PlayerResponse(
                item.player().id(),
                item.player().name(),
                item.player().age(),
                item.player().nationality(),
                item.player().photo(),
                position,
                teamId,
                statistic == null || statistic.team() == null ? null : statistic.team().name(),
                statistic == null || statistic.team() == null ? null : statistic.team().logo(),
                number
        );
    }

    private PlayerStatisticItem pickStatistic(List<PlayerStatisticItem> statistics) {
        if (statistics == null || statistics.isEmpty()) {
            return null;
        }

        return statistics.stream()
                .filter(statistic -> statistic.league() != null)
                .filter(statistic -> statistic.league().id() != null)
                .filter(statistic ->
                        statistic.league().id().equals(KLeagueConstants.KLEAGUE1_ID) ||
                        statistic.league().id().equals(KLeagueConstants.KLEAGUE2_ID)
                )
                .findFirst()
                .orElse(null);
    }

    private PlayerSquadInfo findSquadInfo(Long teamId, Long playerId) {
        if (teamId == null || playerId == null) {
            return null;
        }

        PlayerSquadApiResponse result = footballClient.getPlayerSquad(teamId);
        if (result == null || result.response() == null) {
            return null;
        }

        return result.response().stream()
                .map(PlayerSquadItem::players)
                .filter(players -> players != null && !players.isEmpty())
                .flatMap(List::stream)
                .filter(player -> player.id() != null)
                .filter(player -> player.id().equals(playerId))
                .findFirst()
                .orElse(null);
    }
}
