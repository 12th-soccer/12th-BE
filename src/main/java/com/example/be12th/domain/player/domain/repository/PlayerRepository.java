package com.example.be12th.domain.player.domain.repository;

import com.example.be12th.domain.player.domain.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Page<Player> findByNameContaining(String searchKeyword, Pageable pageable);
}
