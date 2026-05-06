package com.example.be12th.domain.favorite.presnetation.dto.response;

import com.example.be12th.domain.favorite.domain.FavoriteTeam;

public record FavoriteTeamResponse(
        Long teamId,
        String teamImageUrl,
        String teamName
) {
    public static FavoriteTeamResponse from(FavoriteTeam favoriteTeam) {
        return new FavoriteTeamResponse(
                favoriteTeam.getTeamId(),
                favoriteTeam.getTeamLogo(),
                favoriteTeam.getTeamName()
        );
    }
}
