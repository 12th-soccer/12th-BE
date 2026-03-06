package com.example.be12th.domain.club.presentation.dto.response;

import com.example.be12th.domain.club.domain.Club;
import com.example.be12th.domain.match.domain.Match;
import com.example.be12th.domain.match.presentation.dto.response.MatchResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ClubResponse {
    private Long clubId;
    private String clubName;
    private String stadiumName;
    private List<MatchResponse> matches;


    public static ClubResponse from(Club club, List<Match> matches) {

        List<MatchResponse> matchResponses = matches.stream()
                .map(MatchResponse::from)
                .toList();

        return new ClubResponse(
                club.getId(),
                club.getClubName(),
                club.getStadiumName(),
                matchResponses
        );
    }
}
