package com.example.be12th.domain.goal.presentation;

import com.example.be12th.domain.goal.presentation.dto.response.PlayerGoalResponse;
import com.example.be12th.domain.goal.service.GoalQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/goals")
public class GoalController {
    private final GoalQueryService goalQueryService;

    @GetMapping("/{playerId}")
    public PlayerGoalResponse getPlayerGoals(@PathVariable Long playerId, @RequestParam int season, @RequestParam String league) {
        return goalQueryService.execute(playerId, season, league);
    }
}
