package com.example.be12th.domain.favorite.presnetation.dto.response;

import com.example.be12th.domain.favorite.domain.FavoritePlayer;

public record FavoritePlayerResponse(
        Long playerId,
        String playerName,
        String playerImgUrl
) {
    public static FavoritePlayerResponse from(FavoritePlayer favoritePlayer) {
        return new FavoritePlayerResponse(
                favoritePlayer.getPlayerId(),
                favoritePlayer.getPlayerName(),
                favoritePlayer.getPlayerLogo()
        );
    }
}
