package com.example.be12th.domain.player.domain.repository;

import com.example.be12th.domain.player.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
