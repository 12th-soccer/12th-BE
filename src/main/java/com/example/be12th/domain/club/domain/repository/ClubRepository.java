package com.example.be12th.domain.club.domain.repository;

import com.example.be12th.domain.club.domain.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Long> {
    @Query(value = "SELECT * FROM tbl_club ORDER BY club_point DESC",nativeQuery = true)
    List<Club> findAllByOrderByClubPointDesc();
}
