package com.example.be12th.domain.club.domain;

import com.example.be12th.domain.league.LeagueType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "tbl_club")
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id",nullable = false)
    private Long id;

    private Long fotmobId;

    @Enumerated(EnumType.STRING)
    private LeagueType leagueType;

    @Column(name = "club_name",nullable = false)
    private String clubName;

    @Column(name = "stadium_name", nullable = false)
    private String stadiumName;

    @Builder.Default
    @Column(name = "club_point", nullable = false)
    private Integer clubPoint = 0;

    @Builder.Default
    @Column(name = "club_win", nullable = false)
    private Integer clubWin = 0;

    @Builder.Default
    @Column(name = "club_lose", nullable = false)
    private Integer clubLose = 0;

    @Builder.Default
    @Column(name = "club_draw", nullable = false)
    private Integer clubDraw = 0;
}
