package com.example.be12th.domain.team.presentation;

import com.example.be12th.domain.team.presentation.dto.response.TeamResponse;
import com.example.be12th.domain.team.service.TeamQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
public class TeamController {
    private final TeamQueryService teamQueryService;

    @GetMapping("/{teamId}")
    public TeamResponse getTeamById(@PathVariable Long teamId) {
        return teamQueryService.execute(teamId);
    }

    @GetMapping("/kleague1")
    public List<TeamResponse> getKLeague1Teams(@RequestParam int season) {
        return teamQueryService.getKLeague1Teams(season);
    }

    @GetMapping("/kleague2")
    public List<TeamResponse> getKLeague2Teams(@RequestParam int season) {
        return teamQueryService.getKLeague2Teams(season);
    }
}
