package com.example.be12th.domain.club.presentation.dto.response;

import com.example.be12th.domain.club.domain.Club;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClubSearchResponse {
    private Long clubId;
    private String clubName;

    public static ClubSearchResponse from(Club club) {
        return new ClubSearchResponse(
                club.getId(),
                club.getClubName()
        );
    }
}
