package com.example.be12th.domain.favorite.domain;

import com.example.be12th.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(
        name = "tbl_favorite_team",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_favorite_team_user_team", columnNames = {"user_id", "team_id"})
        }
)
@Getter
public class FavoriteTeam {
    @Id
    @Column(nullable = false, name = "favorite_team_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "team_id", nullable = false)
    private Long teamId;

    @Column(name = "favorite_team_name", nullable = false)
    private String teamName;

    @Column(name = "favorite_team_logo", nullable = false)
    private String teamLogo;
}
