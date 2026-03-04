package com.example.be12th.domain.match.service;

import com.example.be12th.domain.match.domain.Match;
import com.example.be12th.domain.match.domain.repository.MatchRepository;
import com.example.be12th.domain.match.presentation.dto.response.MatchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MatchQueryService {
    private final MatchRepository matchRepository;

    @Transactional(readOnly = true)
    public MatchResponse execute(Long matchId){
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("해당 매치를 찾을수 없습니다!"));

        return MatchResponse.from(match);
    }
}
