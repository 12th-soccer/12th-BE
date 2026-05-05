package com.example.be12th.domain.footballapi.service;

import com.example.be12th.domain.footballapi.presentation.dto.external.FixtureApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class FootballClient {

    private final RestClient apiFootballRestClient;

    public FixtureApiResponse getFixtures(String date) {

        return apiFootballRestClient.get()

                .uri(uriBuilder -> uriBuilder

                        .path("/fixtures")

                        .queryParam("date", date)

                        .build())

                .retrieve()

                .body(FixtureApiResponse.class);

    }
}

