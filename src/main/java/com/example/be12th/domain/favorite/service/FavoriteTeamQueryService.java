package com.example.be12th.domain.favorite.service;

import com.example.be12th.domain.favorite.domain.Repository.FavoriteTeamRepository;
import com.example.be12th.domain.favorite.presnetation.dto.response.FavoriteTeamResponse;
import com.example.be12th.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteTeamQueryService {
    private final FavoriteTeamRepository favoriteTeamRepository;
    private final UserFacade userFacade;

    @Transactional(readOnly = true)
    public List<FavoriteTeamResponse> execute() {
        Long userId = userFacade.currentUserId();

        return favoriteTeamRepository.findAllByUserId(userId).stream()
                .map(FavoriteTeamResponse::from)
                .toList();
    }
}
