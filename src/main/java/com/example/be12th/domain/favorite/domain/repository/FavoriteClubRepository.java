package com.example.be12th.domain.favorite.domain.repository;

import com.example.be12th.domain.club.domain.Club;
import com.example.be12th.domain.favorite.domain.FavoriteClub;
import com.example.be12th.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteClubRepository extends JpaRepository<FavoriteClub, Long> {
    List<FavoriteClub> findByUser(User user);
    boolean existsByUserAndClub(User user, Club club);
    User user(User user);
}
