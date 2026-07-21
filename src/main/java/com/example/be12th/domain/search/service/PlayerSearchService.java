package com.example.be12th.domain.search.service;

import com.example.be12th.domain.footballapi.support.KLeagueConstants;
import com.example.be12th.domain.player.domain.Player;
import com.example.be12th.domain.player.domain.repository.PlayerRepository;
import com.example.be12th.domain.search.presentation.dto.PlayerSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlayerSearchService {
    private static final int PAGE_SIZE = 20;

    private final PlayerRepository playerRepository;

    public List<PlayerSearchResponse> execute(String keyword, int season, int page) {
        Pageable pageable = PageRequest.of(Math.max(page, 1) - 1, PAGE_SIZE);
        LinkedHashMap<Long, PlayerSearchResponse> uniquePlayers = new LinkedHashMap<>();

        searchPlayersByLeague(KLeagueConstants.KLEAGUE1_ID, keyword, season, pageable)
                .forEach(player -> uniquePlayers.putIfAbsent(player.playerId(), player));
        searchPlayersByLeague(KLeagueConstants.KLEAGUE2_ID, keyword, season, pageable)
                .forEach(player -> uniquePlayers.putIfAbsent(player.playerId(), player));

        return List.copyOf(uniquePlayers.values());
    }

    private List<PlayerSearchResponse> searchPlayersByLeague(
            Long leagueId,
            String keyword,
            int season,
            Pageable pageable
    ) {
        return playerRepository.findAllByLeagueIdAndSeasonAndNameContainingIgnoreCase(
                        leagueId,
                        season,
                        keyword,
                        pageable
                ).stream()
                .map(this::toPlayerSearchResponse)
                .toList();
    }

    private PlayerSearchResponse toPlayerSearchResponse(Player player) {
        return new PlayerSearchResponse(
                player.getExternalPlayerId(),
                player.getName(),
                player.getPhotoUrl()
        );
    }
}
