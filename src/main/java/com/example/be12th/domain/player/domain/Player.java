package com.example.be12th.domain.player.domain;

import com.example.be12th.domain.club.domain.Club;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_player")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id",nullable = false)
    private Long id;

    @Column(nullable = false,name = "player_name")
    private String name;

    @Column(nullable = false,name = "player_age")
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "player_position")
    private Position position;

    @Column(nullable = false, name = "player_number")
    private int number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

}
