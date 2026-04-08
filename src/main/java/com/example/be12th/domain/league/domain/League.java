package com.example.be12th.domain.league.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_league")
public class League {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private LeagueType leagueType;
}
