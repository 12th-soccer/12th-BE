package com.example.be12th.domain.match.domain;

import com.example.be12th.domain.club.domain.Club;
import com.example.be12th.domain.league.LeagueType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_match")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private LeagueType leagueType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_club_id")
    private Club homeClub;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "away_club_id")
    private Club awayClub;

    @Column(name = "home_score", nullable = false)
    private int homeScore;

    @Column(name = "away_score", nullable = false)
    private int awayScore;

    @Column(name = "match_date", nullable = false)
    private LocalDateTime matchDate;
}
