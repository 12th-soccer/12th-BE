package com.example.be12th.domain.favorite.service;

import com.example.be12th.domain.favorite.domain.Repository.FavoritePlayerRepository;
import com.example.be12th.domain.favorite.presnetation.dto.response.FavoritePlayerResponse;
import com.example.be12th.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoritePlayerQueryService {
    private final FavoritePlayerRepository favoritePlayerRepository;
    private final UserFacade userFacade;

    @Transactional(readOnly = true)
    public List<FavoritePlayerResponse> execute(){
        Long userId = userFacade.currentUserId();

        return favoritePlayerRepository.findAllByUserId(userId).stream()
                .map(FavoritePlayerResponse::from)
                .toList();
    }
}
