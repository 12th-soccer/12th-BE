package com.example.be12th.domain.player.service;

import com.example.be12th.domain.footballapi.support.KLeagueConstants;
import com.example.be12th.domain.player.domain.Player;
import com.example.be12th.domain.player.domain.repository.PlayerRepository;
import com.example.be12th.domain.player.presentation.dto.response.PlayerResponse;
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
public class PlayerQueryService {
    private static final int PAGE_SIZE = 20;

    private final PlayerRepository playerRepository;

    public PlayerResponse execute(Long playerId, int season) {
        return playerRepository.findFirstByExternalPlayerIdAndSeasonOrderByIdDesc(playerId, season)
                .map(this::toPlayerResponse)
                .orElse(null);
    }

    public List<PlayerResponse> getKLeague1Players(int season, int page) {
        return getPlayersByLeague(KLeagueConstants.KLEAGUE1_ID, season, page);
    }

    public List<PlayerResponse> getKLeague2Players(int season, int page) {
        return getPlayersByLeague(KLeagueConstants.KLEAGUE2_ID, season, page);
    }

    public List<PlayerResponse> searchPlayers(String keyword, int season, int page) {
        List<PlayerResponse> kLeague1Players = searchPlayersByLeague(KLeagueConstants.KLEAGUE1_ID, keyword, season, page);
        List<PlayerResponse> kLeague2Players = searchPlayersByLeague(KLeagueConstants.KLEAGUE2_ID, keyword, season, page);

        LinkedHashMap<Long, PlayerResponse> uniquePlayers = new LinkedHashMap<>();

        for (PlayerResponse player : kLeague1Players) {
            uniquePlayers.put(player.playerId(), player);
        }

        for (PlayerResponse player : kLeague2Players) {
            uniquePlayers.put(player.playerId(), player);
        }

        return uniquePlayers.values().stream().toList();
    }

    private List<PlayerResponse> getPlayersByLeague(Long leagueId, int season, int page) {
        return playerRepository.findAllByLeagueIdAndSeason(leagueId, season, pageable(page)).stream()
                .map(this::toPlayerResponse)
                .toList();
    }

    private List<PlayerResponse> searchPlayersByLeague(Long leagueId, String keyword, int season, int page) {
        return playerRepository.findAllByLeagueIdAndSeasonAndNameContainingIgnoreCase(
                        leagueId,
                        season,
                        keyword,
                        pageable(page)
                ).stream()
                .map(this::toPlayerResponse)
                .toList();
    }

    private Pageable pageable(int page) {
        return PageRequest.of(Math.max(page, 1) - 1, PAGE_SIZE);
    }

    private PlayerResponse toPlayerResponse(Player player) {
        return new PlayerResponse(
                player.getExternalPlayerId(),
                player.getName(),
                player.getAge(),
                player.getNationality(),
                player.getPhotoUrl(),
                player.getPosition(),
                player.getTeamId(),
                player.getTeamName(),
                player.getTeamLogo(),
                player.getNumber()
        );
    }
}
