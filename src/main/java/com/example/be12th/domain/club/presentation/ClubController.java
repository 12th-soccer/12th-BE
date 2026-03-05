package com.example.be12th.domain.club.presentation;

import com.example.be12th.domain.club.presentation.dto.response.ClubResponse;
import com.example.be12th.domain.club.service.ClubQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/club")
public class ClubController {
    private final ClubQueryService clubQueryService;

    @GetMapping("/{clubId}")
    @ResponseStatus(HttpStatus.OK)
    public ClubResponse query(@PathVariable Long clubId) {
        return clubQueryService.execute(clubId);
    }
}
