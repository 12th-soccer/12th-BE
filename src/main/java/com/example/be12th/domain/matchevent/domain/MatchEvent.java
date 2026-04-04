package com.example.be12th.domain.matchevent.domain;

import com.example.be12th.domain.match.domain.Match;
import com.example.be12th.domain.player.domain.Player;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "tbl_match_event")
public class MatchEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private Match match;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    private Player player;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private MatchEventType eventType;

    @Column(name = "event_minute", nullable = false)
    private Integer eventMinute;

    @Column(name = "event_description")
    private String description;
}
