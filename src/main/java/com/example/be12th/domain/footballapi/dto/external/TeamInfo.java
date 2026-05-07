package com.example.be12th.domain.footballapi.dto.external;

public record TeamInfo(
        Long id,
        String name,
        String logo,
        Boolean winner
) {
}
