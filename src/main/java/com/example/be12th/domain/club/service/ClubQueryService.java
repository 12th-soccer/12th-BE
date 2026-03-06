package com.example.be12th.domain.club.service;

import com.example.be12th.domain.club.domain.Club;
import com.example.be12th.domain.club.domain.repository.ClubRepository;
import com.example.be12th.domain.club.presentation.dto.response.ClubResponse;
import com.example.be12th.domain.match.domain.Match;
import com.example.be12th.domain.match.domain.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubQueryService {

    private final ClubRepository clubRepository;
    private final MatchRepository matchRepository;

    @Transactional(readOnly = true)
    public ClubResponse execute(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("해당 클럽을 찾을수 없습니다."));

        List<Match> matches = matchRepository.findByHomeClub_IdOrAwayClub_Id(clubId, clubId);
        
        return ClubResponse.from(club,matches);
    }
}
