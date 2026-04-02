package com.example.be12th.domain.goal.domain;

import com.example.be12th.domain.match.domain.Match;
import com.example.be12th.domain.player.domain.Player;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "tbl_goal")
public class Goal {
    @Id
    @Column(name = "goal_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private Match match;

    @Column(name = "goal_time")
    private Integer goalTime;

    @Column(name = "goal_type")
    private GoalType goalType;
}
