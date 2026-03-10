package com.example.be12th.domain.match.presentation.dto.request;

import com.example.be12th.domain.match.domain.Match;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MatchRequest {
    private Long HomeClubId;
    private Long AwayClubId;
    private Integer homeScore;
    private Integer awayScore;
    private LocalDateTime matchDate;

    public static MatchRequest from(Match match) {
        return new MatchRequest(
                match.getHomeClub().getId(),
                match.getAwayClub().getId(),
                match.getHomeScore(),
                match.getAwayScore(),
                match.getMatchDate()
        );
    }
}
