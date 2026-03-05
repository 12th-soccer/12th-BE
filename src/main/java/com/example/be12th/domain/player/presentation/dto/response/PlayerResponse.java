package com.example.be12th.domain.player.presentation.dto.response;

import com.example.be12th.domain.player.domain.Player;
import com.example.be12th.domain.player.domain.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlayerResponse {
    private Long playerId;
    private String name;
    private int age;
    private Position position;
    private int number;
    private String clubName;

    public static PlayerResponse from(Player player) {
        return new PlayerResponse(
          player.getId(),
                player.getName(),
                player.getAge(),
                player.getPosition(),
                player.getNumber(),
                player.getClub().getClubName()
        );
    }
}
