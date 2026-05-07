package com.example.be12th.domain.footballapi.dto.external;

public record VenueInfo(
        Long id,
        String name,
        String address,
        String city,
        Integer capacity
) {
}
