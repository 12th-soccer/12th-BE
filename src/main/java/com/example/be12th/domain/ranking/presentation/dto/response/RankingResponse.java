package com.example.be12th.domain.ranking.presentation.dto.response;

import com.example.be12th.domain.club.domain.Club;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RankingResponse {
    private Long clubId;
    private String clubName;
    private String clubImage;
    private Integer win;
    private Integer lose;
    private Integer draw;
    private Integer point;

    public static RankingResponse from(Club club,int i) {
        return new RankingResponse(
        club.getId(),
        club.getClubName(),
        club.getClubImageUrl(),
        club.getClubWin(),
        club.getClubLose(),
        club.getClubDraw(),
        club.getClubPoint()
        );
    }
}
