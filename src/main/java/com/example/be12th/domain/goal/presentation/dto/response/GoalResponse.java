package com.example.be12th.domain.goal.presentation.dto.response;

import com.example.be12th.domain.goal.domain.Goal;
import com.example.be12th.domain.goal.domain.GoalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class GoalResponse {
    private Long id;
    private Long PlayerId;
    private LocalDateTime matchdate;
    private Integer goalTime;
    private GoalType goalType;

    public static GoalResponse from(Goal goal) {
        return new GoalResponse(
                goal.getId(),
                goal.getPlayer().getId(),
                goal.getMatch().getMatchDate(),
                goal.getGoalTime(),
                goal.getGoalType()
        );
    }
}
