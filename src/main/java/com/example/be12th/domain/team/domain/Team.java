package com.example.be12th.domain.team.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(
        name = "tbl_football_team",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_football_team_external_league_season",
                        columnNames = {"external_team_id", "league_id", "season"}
                )
        }
)
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    @Column(name = "external_team_id", nullable = false)
    private Long externalTeamId;

    @Column(name = "league_id", nullable = false)
    private Long leagueId;

    @Column(nullable = false)
    private Integer season;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "country")
    private String country;

    @Column(name = "founded")
    private Integer founded;

    @Column(name = "logo_url", length = 1000)
    private String logoUrl;

    @Column(name = "venue_id")
    private Long venueId;

    @Column(name = "venue_name")
    private String venueName;

    @Column(name = "venue_address")
    private String venueAddress;

    @Column(name = "venue_city")
    private String venueCity;

    @Column(name = "venue_capacity")
    private Integer venueCapacity;

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
            String country,
            Integer founded,
            String logoUrl,
            Long venueId,
            String venueName,
            String venueAddress,
            String venueCity,
            Integer venueCapacity
    ) {
        this.name = name;
        this.country = country;
        this.founded = founded;
        this.logoUrl = logoUrl;
        this.venueId = venueId;
        this.venueName = venueName;
        this.venueAddress = venueAddress;
        this.venueCity = venueCity;
        this.venueCapacity = venueCapacity;
        this.syncedAt = LocalDateTime.now();
    }
}
