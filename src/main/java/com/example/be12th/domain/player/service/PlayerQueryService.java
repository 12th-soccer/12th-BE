package com.example.be12th.domain.player.service;

import com.example.be12th.domain.player.domain.Player;
import com.example.be12th.domain.player.domain.repository.PlayerRepository;
import com.example.be12th.domain.player.presentation.dto.response.PlayerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlayerQueryService {

    private final PlayerRepository playerRepository;

    @Transactional(readOnly = true)
    public PlayerResponse execute(Long playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("해당 선수를 찾을수 없습니다"));

        return PlayerResponse.from(player);
    }

}
