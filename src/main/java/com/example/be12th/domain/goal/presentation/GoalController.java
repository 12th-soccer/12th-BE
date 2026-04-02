package com.example.be12th.domain.goal.presentation;

import com.example.be12th.domain.goal.presentation.dto.response.GoalResponse;
import com.example.be12th.domain.goal.service.GoalQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/goal")
@RequiredArgsConstructor
public class GoalController {
    private final GoalQueryService goalQueryService;

    @GetMapping("/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public List<GoalResponse> getGoal(@PathVariable Long playerId) {
        return goalQueryService.execute(playerId);
    }
}
