package com.example.be12th.domain.club.domain;

import com.example.be12th.domain.match.domain.Match;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
}
