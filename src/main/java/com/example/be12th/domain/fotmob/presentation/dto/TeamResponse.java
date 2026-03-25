package com.example.be12th.domain.fotmob.presentation.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class TeamResponse {

    private SquadWrapper squad;

    @Getter
    public static class SquadWrapper {
        private List<Squad> squad;
    }

    @Getter
    public static class Squad {
        private String title;
        private List<PlayerDto> members;
    }

    @Getter
    public static class PlayerDto {
        private Long id;
        private String name;
        private Integer age;
        private Integer shirtNumber;
        private Integer positionId;
    }
}