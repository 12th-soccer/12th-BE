package com.example.be12th.domain.favorite.service;

import com.example.be12th.domain.favorite.domain.FavoriteTeam;
import com.example.be12th.domain.favorite.domain.Repository.FavoriteTeamRepository;
import com.example.be12th.domain.team.presentation.dto.response.TeamResponse;
import com.example.be12th.domain.team.service.TeamQueryService;
import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoriteTeamService {
    private final FavoriteTeamRepository favoriteTeamRepository;
    private final UserRepository userRepository;
    private final UserFacade userFacade;
    private final TeamQueryService teamQueryService;

    @Transactional
    public void execute(Long teamId) {
        Long userId = userFacade.currentUserId();

        if (favoriteTeamRepository.existsByUserIdAndTeamId(userId, teamId)) {
            throw new RuntimeException("이미 즐겨찾기된 구단입니다.");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        TeamResponse team = teamQueryService.execute(teamId);
        if (team == null) {
            throw new RuntimeException("해당 구단을 찾을 수 없습니다.");
        }
        favoriteTeamRepository.save(
                FavoriteTeam.builder()
                        .user(user)
                        .teamId(team.teamId())
                        .teamName(team.name())
                        .teamLogo(team.logo())
                        .build()
        );
    }
}
