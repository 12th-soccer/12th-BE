package com.example.be12th.domain.match.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(
        name = "tbl_football_match",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_football_match_external_fixture",
                        columnNames = {"external_fixture_id"}
                )
        }
)
public class FootballMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private Long id;

    @Column(name = "external_fixture_id", nullable = false)
    private Long externalFixtureId;

    @Column(name = "league_id", nullable = false)
    private Long leagueId;

    @Column(name = "season", nullable = false)
    private Integer season;

    @Column(name = "match_date", nullable = false)
    private String matchDate;

    @Column(name = "home_team_id")
    private Long homeTeamId;

    @Column(name = "home_team_name")
    private String homeTeamName;

    @Column(name = "home_team_logo", length = 1000)
    private String homeTeamLogo;

    @Column(name = "away_team_id")
    private Long awayTeamId;

    @Column(name = "away_team_name")
    private String awayTeamName;

    @Column(name = "away_team_logo", length = 1000)
    private String awayTeamLogo;

    @Column(name = "home_score")
    private Integer homeScore;

    @Column(name = "away_score")
    private Integer awayScore;

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
            String matchDate,
            Long homeTeamId,
            String homeTeamName,
            String homeTeamLogo,
            Long awayTeamId,
            String awayTeamName,
            String awayTeamLogo,
            Integer homeScore,
            Integer awayScore
    ) {
        this.matchDate = matchDate;
        this.homeTeamId = homeTeamId;
        this.homeTeamName = homeTeamName;
        this.homeTeamLogo = homeTeamLogo;
        this.awayTeamId = awayTeamId;
        this.awayTeamName = awayTeamName;
        this.awayTeamLogo = awayTeamLogo;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.syncedAt = LocalDateTime.now();
    }
}
