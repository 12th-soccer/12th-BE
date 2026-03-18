package com.example.be12th.domain.favorite.service;

import com.example.be12th.domain.favorite.domain.FavoriteClub;
import com.example.be12th.domain.favorite.domain.repository.FavoriteClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoriteClubDeleteService {
    private final FavoriteClubRepository favoriteClubRepository;

    @Transactional
    public void execute(Long clubId){
        FavoriteClub favoriteClub = favoriteClubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("해당 관심구단을 찾을수 없습니다."));
        favoriteClubRepository.delete(favoriteClub);
    }
}
