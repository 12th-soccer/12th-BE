package com.example.be12th.domain.footballapi.presentation.dto.external;

public record PlayerInfo(
        Long id,
        String name,
        Integer age,
        String nationality,
        String photo
) {
}
