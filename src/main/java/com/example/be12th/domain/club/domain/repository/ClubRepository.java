package com.example.be12th.domain.club.domain.repository;

import com.example.be12th.domain.club.domain.Club;
import com.example.be12th.domain.player.domain.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface ClubRepository extends JpaRepository<Club, Long> {
    @Query(value = "SELECT * FROM tbl_club ORDER BY club_point DESC",nativeQuery = true)
    List<Club> findAllByOrderByClubPointDesc();

    Optional<Club> findById(Long id);

    Page<Club> findByClubNameContaining(String searchKeyword, Pageable pageable);

    Optional<Club> findByFotmobId(Long fotmobId);
}
