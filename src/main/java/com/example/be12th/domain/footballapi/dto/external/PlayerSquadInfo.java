package com.example.be12th.domain.footballapi.dto.external;

public record PlayerSquadInfo(
        Long id,
        String name,
        Integer age,
        Integer number,
        String position,
        String photo
) {
}
