package com.example.be12th.domain.player.presentation.dto.response;

import com.example.be12th.domain.player.domain.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlayerSearchResponse {
    private Long playerId;
    private String name;

    public static PlayerSearchResponse from(Player player) {
        return new PlayerSearchResponse(
                player.getId(),
                player.getName().trim()
        );
    }
}
