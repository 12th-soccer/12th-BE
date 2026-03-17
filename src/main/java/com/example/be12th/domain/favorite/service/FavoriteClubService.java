package com.example.be12th.domain.favorite.service;

import com.example.be12th.domain.club.domain.Club;
import com.example.be12th.domain.club.domain.repository.ClubRepository;
import com.example.be12th.domain.favorite.domain.FavoriteClub;
import com.example.be12th.domain.favorite.domain.repository.FavoriteClubRepository;
import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteClubService {
    private final FavoriteClubRepository favoriteClubRepository;
    private final UserRepository userRepository;
    private final ClubRepository clubRepository;
    private final UserFacade userFacade;

    public void execute(Long clubId) {
        User user = userRepository.findById((userFacade.currentUserId()))
                .orElseThrow(() -> new RuntimeException("유저를 찾을수없습니다."));

        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("해당 구단 아이디를 찾을수 없습니다."));

        final Boolean ExistUserAndClub = favoriteClubRepository.existsByUserAndClub(user , club);
        if (ExistUserAndClub) {
            throw new RuntimeException("이미 즐겨찾기된 구단입니다..");
        }
        FavoriteClub favoriteClub =  FavoriteClub.builder()
                .user(user)
                .club(club)
                .build();
        favoriteClubRepository.save(favoriteClub);
    }
}
