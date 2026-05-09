package com.example.be12th.domain.team.presentation;

import com.example.be12th.domain.team.presentation.dto.response.TeamDetailResponse;
import com.example.be12th.domain.team.presentation.dto.response.TeamResponse;
import com.example.be12th.domain.team.service.TeamDetailQueryService;
import com.example.be12th.domain.team.service.TeamQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
public class TeamController {
    private final TeamQueryService teamQueryService;
    private final TeamDetailQueryService teamDetailQueryService;

    @GetMapping("/{teamId}")
    public TeamDetailResponse getTeamById(@PathVariable Long teamId, @RequestParam(required = false) Integer season) {
        return teamDetailQueryService.execute(teamId, season);
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
