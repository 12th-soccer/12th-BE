package com.example.be12th.domain.favorite.domain;

import com.example.be12th.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(
        name = "tbl_favorite_player",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_favorite_player_user_player", columnNames = {"user_id", "player_id"})
        }
)
@Getter
public class FavoritePlayer {
    @Id
    @Column(nullable = false, name = "favorite_player_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "player_id", nullable = false)
    private Long playerId;

    @Column(name = "favorite_player_name", nullable = false)
    private String playerName;

    @Column(name = "favorite_player_logo", nullable = false)
    private String playerLogo;
}
