package com.example.be12th.domain.footballapi.presentation.dto.external;

public record VenueInfo(
        Long id,
        String name,
        String address,
        String city,
        Integer capacity
) {
}
