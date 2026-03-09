package com.example.be12th.domain.match.service;

import com.example.be12th.domain.match.domain.Match;
import com.example.be12th.domain.match.domain.repository.MatchRepository;
import com.example.be12th.domain.match.presentation.dto.response.MatchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CaleanderQueryService {

    private final MatchRepository matchRepository;

    @Transactional(readOnly = true)
    public List<MatchResponse> execute(LocalDate date) {

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(23, 59, 59);

        List<Match> match = matchRepository.findByMatchDateBetween(start, end);

        return match.stream()
                .map(MatchResponse::from)
                .toList();
    }
}
