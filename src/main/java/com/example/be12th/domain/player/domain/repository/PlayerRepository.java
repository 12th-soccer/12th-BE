package com.example.be12th.domain.player.domain.repository;

import com.example.be12th.domain.player.domain.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Page<Player> findByNameContaining(String searchKeyword, Pageable pageable);

    Optional<Player> findByFotmobId(Long fotmobId);
}
