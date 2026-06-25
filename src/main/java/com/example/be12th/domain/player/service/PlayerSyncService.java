package com.example.be12th.domain.player.service;

import com.example.be12th.domain.footballapi.client.FootballClient;
import com.example.be12th.domain.footballapi.dto.external.PlayerApiResponse;
import com.example.be12th.domain.footballapi.dto.external.PlayerItem;
import com.example.be12th.domain.footballapi.dto.external.PlayerStatisticItem;
import com.example.be12th.domain.player.domain.Player;
import com.example.be12th.domain.player.domain.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlayerSyncService {

    private final PlayerRepository playerRepository;
    private final FootballClient footballClient;

    @Transactional
    public void execute(Long leagueId, int season, int page) {
        PlayerApiResponse result = footballClient.getPlayersByLeague(leagueId, season, page);

        if (result == null || result.response() == null) {
            return;
        }

        for (PlayerItem item : result.response()) {
            if (!isValid(item, leagueId)) {
                continue;
            }

            upsertPlayer(leagueId, season, item);
        }
    }

    private void upsertPlayer(Long leagueId, int season, PlayerItem item) {
        PlayerStatisticItem statistic = pickStatistic(item, leagueId);

        Long externalPlayerId = item.player().id();
        Long teamId = statistic.team().id();

        Player player = playerRepository
                .findByExternalPlayerIdAndLeagueIdAndSeasonAndTeamId(
                        externalPlayerId,
                        leagueId,
                        season,
                        teamId
                )
                .orElseGet(() -> Player.builder()
                        .externalPlayerId(externalPlayerId)
                        .leagueId(leagueId)
                        .season(season)
                        .teamId(teamId)
                        .name(item.player().name())
                        .build());

        player.update(
                item.player().name(),
                item.player().age(),
                item.player().nationality(),
                item.player().photo(),
                statistic.games() == null ? null : statistic.games().position(),
                statistic.games() == null ? null : statistic.games().number(),
                statistic.team().name(),
                statistic.team().logo(),
                statistic.goals() == null ? null : statistic.goals().total()
        );

        playerRepository.save(player);
    }

    private boolean isValid(PlayerItem item, Long leagueId) {
        if (item == null || item.player() == null) {
            return false;
        }

        if (item.player().id() == null || item.player().name() == null) {
            return false;
        }

        PlayerStatisticItem statistic = pickStatistic(item, leagueId);

        return statistic != null
                && statistic.team() != null
                && statistic.team().id() != null;
    }

    private PlayerStatisticItem pickStatistic(PlayerItem item, Long leagueId) {
        if (item == null || item.statistics() == null || item.statistics().isEmpty()) {
            return null;
        }

        return item.statistics().stream()
                .filter(stat -> stat.league() != null)
                .filter(stat -> leagueId.equals(stat.league().id()))
                .findFirst()
                .orElse(null);
    }
}
