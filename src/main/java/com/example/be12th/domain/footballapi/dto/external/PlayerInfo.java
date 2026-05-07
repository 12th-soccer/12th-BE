package com.example.be12th.domain.footballapi.dto.external;

public record PlayerInfo(
        Long id,
        String name,
        Integer age,
        String nationality,
        String photo
) {
}
