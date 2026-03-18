package com.example.be12th.domain.player.service;

import com.example.be12th.domain.favorite.domain.FavoritePlayer;
import com.example.be12th.domain.favorite.domain.repository.FavoritePlayerRepository;
import com.example.be12th.domain.player.presentation.dto.response.PlayerFavoriteResponse;
import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerFavoriteQueryAllService {
    private final FavoritePlayerRepository favoritePlayerRepository;
    private final UserRepository userRepository;
    private final UserFacade userFacade;

    @Transactional(readOnly = true)
    public List<PlayerFavoriteResponse> execute() {
        User user = userRepository.findById(userFacade.currentUserId())
                .orElseThrow(()-> new RuntimeException("유저를 찾을수없습니다."));

        List<FavoritePlayer> favoritePlayers = favoritePlayerRepository.findByUser(user);

        return favoritePlayers.stream()
                .map(favoritePlayer -> PlayerFavoriteResponse.from(favoritePlayer.getPlayer()))
                .collect(Collectors.toList());
    }
}
