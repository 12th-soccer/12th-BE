package com.example.be12th.domain.player.service;

import com.example.be12th.domain.player.domain.repository.PlayerRepository;
import com.example.be12th.domain.player.presentation.dto.response.PlayerSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlayerSearchService {
    private final PlayerRepository playerRepository;

    @Transactional
    public Page<PlayerSearchResponse> execute(String keyword, Pageable pageable) {
        return playerRepository
                .findByNameContaining(keyword, pageable)
                .map(PlayerSearchResponse::from);
    }
}
