package com.example.be12th.domain.ranking.service;

import com.example.be12th.domain.club.domain.Club;
import com.example.be12th.domain.club.domain.repository.ClubRepository;
import com.example.be12th.domain.ranking.presentation.dto.response.RankingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingQueryAllService {
    private final ClubRepository clubRepository;

    public List<RankingResponse> execute(){
        List<Club> allClubRanking = clubRepository.findAllByOrderByClubPointDesc();
        List<RankingResponse> rankingResponses = new ArrayList<>();

        for (int i = 0; i < allClubRanking.size(); i++) {
        rankingResponses.add(RankingResponse.from(allClubRanking.get(i),i+1));
        }
        return rankingResponses;
    }
}
