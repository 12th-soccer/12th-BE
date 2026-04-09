package com.example.be12th.domain.club.presentation.dto.response;

import com.example.be12th.domain.club.domain.Club;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClubFavoriteResponse {
    private Long clubId;
    private String clubImageUrl;
    private String clubName;

    public static ClubFavoriteResponse from(Club club) {
        return new ClubFavoriteResponse(
                club.getId(),
                club.getClubImageUrl(),
                club.getClubName()
        );
    }
}
