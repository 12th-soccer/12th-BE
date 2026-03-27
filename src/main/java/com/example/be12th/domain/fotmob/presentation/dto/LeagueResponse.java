package com.example.be12th.domain.fotmob.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LeagueResponse {

    private DataWrapper data;

    @Getter
    public static class DataWrapper {
        private Table table;
    }

    @Getter
    public static class Table {
        private List<ClubDto> all;
    }

    @Getter
    public static class ClubDto {
        private Long id;
        private String name;
        private int wins;
        private int draws;
        private int losses;
        private int pts;
    }
}
