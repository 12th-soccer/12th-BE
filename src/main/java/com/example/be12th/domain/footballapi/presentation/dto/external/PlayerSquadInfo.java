package com.example.be12th.domain.footballapi.presentation.dto.external;

public record PlayerSquadInfo(
        Long id,
        String name,
        Integer age,
        Integer number,
        String position,
        String photo
) {
}
