package com.example.be12th.domain.player.presentation.dto.response;

import com.example.be12th.domain.player.domain.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlayerFavoriteResponse {
    private Long playerId;
    private String playerName;

    public static PlayerFavoriteResponse from(Player player) {
        return new PlayerFavoriteResponse(
                player.getId(),
                player.getName()
        );
    }
}
