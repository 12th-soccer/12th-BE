package com.example.be12th.domain.fotmob.service;

import com.example.be12th.domain.club.domain.Club;
import com.example.be12th.domain.club.domain.repository.ClubRepository;
import com.example.be12th.domain.fotmob.presentation.dto.LeagueResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final FotmobService fotmobService;
    private final ObjectMapper objectMapper;

    @Transactional
    public void execute() throws Exception {

        String json = fotmobService.getLeagueData();

        List<LeagueResponse> leagueResponses = objectMapper.readValue(
                json,
                new TypeReference<List<LeagueResponse>>() {}
        );

        LeagueResponse leagueResponse = leagueResponses.get(0);

        List<LeagueResponse.ClubDto> clubs =
                leagueResponse.getData().getTable().getAll();

        List<Club> clubEntities = clubs.stream()
                .map(dto -> Club.builder()
                        .clubName(dto.getName())
                        .clubWin(dto.getWins())
                        .clubDraw(dto.getDraws())
                        .clubLose(dto.getLosses())
                        .clubPoint(dto.getPts())
                        .stadiumName("임시경기장")
                        .fotmobId(dto.getId())
                        .build()
                )
                .toList();
        clubRepository.saveAll(clubEntities);
    }
}