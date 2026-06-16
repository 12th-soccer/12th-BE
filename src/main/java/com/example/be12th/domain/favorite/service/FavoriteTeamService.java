package com.example.be12th.domain.favorite.service;

import com.example.be12th.domain.favorite.domain.FavoriteTeam;
import com.example.be12th.domain.favorite.domain.Repository.FavoriteTeamRepository;
import com.example.be12th.domain.team.presentation.dto.response.TeamResponse;
import com.example.be12th.domain.team.service.TeamQueryService;
import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.facade.UserFacade;
import com.example.be12th.global.error.exception.App12thException;
import com.example.be12th.global.error.exception.ErrorCode;
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
            throw new App12thException(ErrorCode.FAVORITE_TEAM_ALREADY_EXISTS);
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new App12thException(ErrorCode.USER_NOT_FOUND));

        TeamResponse team = teamQueryService.execute(teamId);
        if (team == null) {
            throw new App12thException(ErrorCode.TEAM_NOT_FOUND);
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
