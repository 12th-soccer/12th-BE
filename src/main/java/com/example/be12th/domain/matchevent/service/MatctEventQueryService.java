package com.example.be12th.domain.matchevent.service;

import com.example.be12th.domain.match.domain.repository.MatchRepository;
import com.example.be12th.domain.matchevent.domain.repository.MatchEventRepository;
import com.example.be12th.domain.matchevent.presentation.dto.MatchEventResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatctEventQueryService {

    private final MatchEventRepository matchEventRepository;
    private final MatchRepository matchRepository;

    public List<MatchEventResponse> execute(Long matchId){
        matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("해당 경기를 찾을수없습니다."));

        return matchEventRepository.findByMatchId(matchId).stream()
                .map(MatchEventResponse::from)
                .collect(Collectors.toList());
    }
}
