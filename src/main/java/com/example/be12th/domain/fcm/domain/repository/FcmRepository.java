package com.example.be12th.domain.fcm.domain.repository;

import com.example.be12th.domain.fcm.domain.FcmToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FcmRepository extends JpaRepository<FcmToken, Long> {
    @Query("""
    select ft.token
    from FcmToken ft
    where ft.user.id = :userId
""")
    List<String> findTokensByUserId(Long userId);
    boolean existsByUserIdAndToken(Long userId, String token);
}
