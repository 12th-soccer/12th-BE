package com.example.be12th.domain.join.domain.repository;

import com.example.be12th.domain.join.domain.Join;
import com.example.be12th.domain.recruitment.domain.Recruitment;
import com.example.be12th.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JoinRepository extends JpaRepository<Join, Long> {
    List<Join> findByUser(User user);

    boolean existsByUserAndRecruitment(User user, Recruitment recruitment);
    long countByRecruitment(Recruitment recruitment);
}
