package com.example.be12th.domain.favorite.service;

import com.example.be12th.domain.favorite.domain.FavoriteTeam;
import com.example.be12th.domain.favorite.domain.Repository.FavoriteTeamRepository;
import com.example.be12th.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoriteTeamDeleteService {
    private final FavoriteTeamRepository favoriteTeamRepository;
    private final UserFacade userFacade;

    @Transactional
    public void execute(Long teamId) {
        Long userId = userFacade.currentUserId();

        FavoriteTeam favoriteTeam = favoriteTeamRepository.findByUserIdAndTeamId(userId, teamId)
                .orElseThrow(() -> new RuntimeException("즐겨찾기한 구단을 찾을 수 없습니다."));

        favoriteTeamRepository.delete(favoriteTeam);
    }
}
