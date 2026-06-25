package com.example.be12th.domain.player.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(
        name = "tbl_football_player",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_football_player_external_league_season_team",
                        columnNames = {"external_player_id", "league_id", "season", "team_id"}
                )
        }
)
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private Long id;

    @Column(name = "external_player_id", nullable = false)
    private Long externalPlayerId;

    @Column(name = "league_id", nullable = false)
    private Long leagueId;

    @Column(name = "season", nullable = false)
    private Integer season;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "photo_url", length = 1000)
    private String photoUrl;

    @Column(name = "position")
    private String position;

    @Column(name = "number")
    private Integer number;

    @Column(name = "team_id", nullable = false)
    private Long teamId;

    @Column(name = "team_name")
    private String teamName;

    @Column(name = "team_logo", length = 1000)
    private String teamLogo;

    @Column(name = "goals_total")
    private Integer goalsTotal;

    @Column(name = "synced_at", nullable = false)
    private LocalDateTime syncedAt;

    @PrePersist
    void prePersist() {
        if (syncedAt == null) {
            syncedAt = LocalDateTime.now();
        }
    }

    @PreUpdate
    void preUpdate() {
        syncedAt = LocalDateTime.now();
    }

    public void update(
            String name,
            Integer age,
            String nationality,
            String photoUrl,
            String position,
            Integer number,
            String teamName,
            String teamLogo,
            Integer goalsTotal
    ) {
        this.name = name;
        this.age = age;
        this.nationality = nationality;
        this.photoUrl = photoUrl;
        this.position = position;
        this.number = number;
        this.teamName = teamName;
        this.teamLogo = teamLogo;
        this.goalsTotal = goalsTotal;
        this.syncedAt = LocalDateTime.now();
    }
}
