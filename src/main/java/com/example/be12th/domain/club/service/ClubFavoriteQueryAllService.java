package com.example.be12th.domain.club.service;

import com.example.be12th.domain.club.presentation.dto.ClubFavoriteResponse;
import com.example.be12th.domain.favorite.domain.FavoriteClub;
import com.example.be12th.domain.favorite.domain.repository.FavoriteClubRepository;
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
public class ClubFavoriteQueryAllService {
    private final FavoriteClubRepository favoriteClubRepository;
    private final UserRepository userRepository;
    private final UserFacade userFacade;

    @Transactional(readOnly = true)
    public List<ClubFavoriteResponse> execute() {
        User user = userRepository.findById(userFacade.currentUserId())
                .orElseThrow(() -> new RuntimeException("현재 유저없음."));

        List<FavoriteClub> favoriteClubs = favoriteClubRepository.findByUser(user);

        return favoriteClubs.stream()
                .map(favoriteClub -> ClubFavoriteResponse.from(favoriteClub.getClub()))
                .collect(Collectors.toList());
    }
}
