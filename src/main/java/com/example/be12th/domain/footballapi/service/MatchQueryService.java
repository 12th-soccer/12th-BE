package com.example.be12th.domain.footballapi.service;

import com.example.be12th.domain.footballapi.presentation.dto.external.FixtureApiResponse;
import com.example.be12th.domain.footballapi.presentation.dto.response.MatchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchQueryService {

    private final FootballClient apiFootballClient;

    public List<MatchResponse> getMatches(String date) {
        FixtureApiResponse result = apiFootballClient.getFixtures(date);

        if (result == null || result.response() == null) {
            return List.of();
        }

        return result.response().stream()
                .map(MatchResponse::from)
                .toList();
    }
}
