package com.example.be12th.domain.fotmob.service;

import com.example.be12th.domain.club.domain.Club;
import com.example.be12th.domain.club.domain.repository.ClubRepository;
import com.example.be12th.domain.fotmob.presentation.dto.TeamResponse;
import com.example.be12th.domain.player.domain.Player;
import com.example.be12th.domain.player.domain.Position;
import com.example.be12th.domain.player.domain.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerImportService {
    private final PlayerRepository playerRepository;
    private final ClubRepository clubRepository;
    private final ObjectMapper objectMapper;
    private final FotmobService fotmobService;

    @Transactional
    public void execute() throws Exception {

        List<Club> clubs = clubRepository.findAll();

        for (Club club : clubs) {

            try {
                Thread.sleep(1500);

                Long fotmobId = club.getFotmobId();
                String json = fotmobService.getTeamDetails(fotmobId);

                TeamResponse response = objectMapper.readValue(json, TeamResponse.class);

                if (response.getSquad() == null) continue;
                if (response.getSquad().getSquad() == null) continue;

                for (TeamResponse.Squad squad : response.getSquad().getSquad()) {

                    if (squad.getMembers() == null) continue;

                    for (TeamResponse.PlayerDto dto : squad.getMembers()) {

                        Player player = playerRepository
                                .findByFotmobId(dto.getId())
                                .orElse(Player.builder().build());

                        Position position =
                                (dto.getPositionId() == null || dto.getPositionId() == 0)
                                        ? Position.FW
                                        : Position.from(dto.getPositionId());

                        player.update(
                                dto.getId(),
                                dto.getName(),
                                dto.getAge() == null ? 0 : dto.getAge(),
                                dto.getShirtNumber() == null ? 0 : dto.getShirtNumber(),
                                position,
                                club
                        );

                        playerRepository.save(player);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("실패 clubId = " + club.getFotmobId());
            }
        }
    }
}
