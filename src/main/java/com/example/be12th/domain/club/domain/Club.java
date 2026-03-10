package com.example.be12th.domain.club.domain;

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

    @Column(name = "club_name",nullable = false)
    private String clubName;

    @Column(name = "stadium_name", nullable = false)
    private String stadiumName;

    @Column(name = "club_point", nullable = false)
    private Integer clubPoint = 0;

    @Column(name = "club_win", nullable = false)
    private Integer clubWin = 0;

    @Column(name = "club_lose", nullable = false)
    private Integer clubLose = 0;

    @Column(name = "club_draw", nullable = false)
    private Integer clubDraw = 0;
}
