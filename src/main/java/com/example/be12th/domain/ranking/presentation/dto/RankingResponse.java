package com.example.be12th.domain.ranking.presentation.dto;

public record RankingResponse(
        Integer rank,
        Long teamId,
        String teamName,
        String teamLogo,
        Integer points,
        Integer played,
        Integer win,
        Integer draw,
        Integer lose
) {
    public static RankingResponse from(StandingItem item){
        return new RankingResponse(
                item.rank(),
                item.team().id(),
                item.team().name(),
                item.team().logo(),
                item.points(),
                item.all().played(),
                item.all().win(),
                item.all().draw(),
                item.all().lose()
        );
    }
}
