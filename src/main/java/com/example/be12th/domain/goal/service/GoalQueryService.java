package com.example.be12th.domain.goal.service;

import com.example.be12th.domain.goal.domain.repository.GoalRepository;
import com.example.be12th.domain.goal.presentation.dto.response.GoalResponse;
import com.example.be12th.domain.player.domain.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalQueryService {
    private final GoalRepository goalRepository;
    private final PlayerRepository playerRepository;

        @Transactional(readOnly = true)
        public List<GoalResponse> execute(Long playerId) {
            playerRepository.findById(playerId)
                    .orElseThrow(() -> new IllegalArgumentException("Player not found"));

            return goalRepository.findByPlayerId(playerId).stream()
                    .map(GoalResponse::from)
                    .toList();
        }
    }
