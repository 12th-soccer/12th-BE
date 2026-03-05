package com.example.be12th.domain.player.presentation;

import com.example.be12th.domain.player.presentation.dto.response.PlayerResponse;
import com.example.be12th.domain.player.service.PlayerQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/player")
public class PlayerController {
    private final PlayerQueryService playerQueryService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PlayerResponse Query(Long playerId){
        return playerQueryService.execute(playerId);
    }
}
