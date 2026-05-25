package com.example.be12th.domain.search.service;

import com.example.be12th.domain.footballapi.client.FootballClient;
import com.example.be12th.domain.footballapi.dto.external.*;
import com.example.be12th.domain.footballapi.support.KLeagueConstants;
import com.example.be12th.domain.search.presentation.dto.PlayerSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerSearchService {
    private static final int MAX_FALLBACK_PAGES = 20;

    private final FootballClient footballClient;

    public List<PlayerSearchResponse> execute(String keyword, int season, int page) {
        List<PlayerSearchResponse> kLeague1Players = searchPlayersByLeague(KLeagueConstants.KLEAGUE1_ID, keyword, season, page);
        List<PlayerSearchResponse> kLeague2Players = searchPlayersByLeague(KLeagueConstants.KLEAGUE2_ID, keyword, season, page);

        return java.util.stream.Stream.concat(kLeague1Players.stream(), kLeague2Players.stream())
                .distinct()
                .toList();
    }

    private List<PlayerSearchResponse> searchPlayersByLeague(Long leagueId, String keyword, int season, int page) {
        PlayerApiResponse result = footballClient.searchPlayersByLeague(leagueId, keyword, season, page);

        if (result == null || result.response() == null) {
            return searchPlayersByLeaguePages(leagueId, keyword, season);
        }

        List<PlayerSearchResponse> players = result.response().stream()
                .map(item -> toSearchPlayerResponse(item, season))
                .filter(player -> player != null)
                .toList();

        if (players.isEmpty()) {
            return searchPlayersByLeaguePages(leagueId, keyword, season);
        }

        return players;
    }

    private List<PlayerSearchResponse> searchPlayersByLeaguePages(Long leagueId, String keyword, int season) {
        List<PlayerSearchResponse> players = new ArrayList<>();
        String normalizedKeyword = normalize(keyword);

        for (int page = 1; page <= MAX_FALLBACK_PAGES; page++) {
            PlayerApiResponse result = footballClient.getPlayersByLeague(leagueId, season, page);

            if (result == null || result.response() == null || result.response().isEmpty()) {
                break;
            }

            List<PlayerSearchResponse> pagePlayers = result.response().stream()
                    .filter(item -> matchesKeyword(item, normalizedKeyword))
                    .map(item -> toSearchPlayerResponse(item, season))
                    .filter(player -> player != null)
                    .toList();

            players.addAll(pagePlayers);
        }

        return players;
    }

    private boolean matchesKeyword(PlayerItem item, String normalizedKeyword) {
        if (item == null || item.player() == null || item.player().name() == null) {
            return false;
        }

        return normalize(item.player().name()).contains(normalizedKeyword);
    }

    private String normalize(String value) {
        if (value == null) {
            return "";
        }

        return value.toLowerCase()
                .replace(" ", "")
                .replace("-", "");
    }

    private PlayerSearchResponse toSearchPlayerResponse(PlayerItem item, int season) {
        PlayerSearchResponse response = toPlayerResponse(item, false);

        if (item == null || item.player() == null || item.player().id() == null) {
            return response;
        }

        PlayerApiResponse detailResult = footballClient.getPlayerDetail(item.player().id(), season);

        if (detailResult == null || detailResult.response() == null || detailResult.response().isEmpty()) {
            return response;
        }

        return toPlayerResponse(detailResult.response().get(0), false);
    }

    private PlayerSearchResponse toPlayerResponse(PlayerItem item, boolean includeSquadInfo) {
        if (item == null || item.player() == null) {
            return null;
        }

        PlayerStatisticItem statistic = pickStatistic(item.statistics());
        return new PlayerSearchResponse(
                item.player().id(),
                item.player().name(),
                item.player().photo()
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

}
