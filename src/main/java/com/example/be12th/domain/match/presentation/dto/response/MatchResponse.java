package com.example.be12th.domain.match.presentation.dto.response;

import com.example.be12th.domain.league.LeagueType;
import com.example.be12th.domain.match.domain.Match;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MatchResponse {
    private Long matchId;
    private LeagueType leagueType;
    private LocalDateTime matchDate;
    private String homeTeamName;
    private String awayTeamName;
    private Integer homeTeamScore;
    private Integer awayTeamScore;


    public static MatchResponse from(Match match) {
        return new MatchResponse(
                match.getId(),
                match.getLeagueType(),
                match.getMatchDate(),
                match.getHomeClub().getClubName(),
                match.getAwayClub().getClubName(),
                match.getHomeScore(),
                match.getAwayScore()
        );
    }
}
