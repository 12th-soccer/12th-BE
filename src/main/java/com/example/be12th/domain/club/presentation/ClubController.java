package com.example.be12th.domain.club.presentation;

import com.example.be12th.domain.club.presentation.dto.response.ClubResponse;
import com.example.be12th.domain.club.presentation.dto.response.ClubSearchResponse;
import com.example.be12th.domain.club.service.ClubQueryService;
import com.example.be12th.domain.club.service.ClubSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/club")
public class ClubController {
    private final ClubQueryService clubQueryService;
    private final ClubSearchService clubSearchService;

    @GetMapping("/{clubId}")
    @ResponseStatus(HttpStatus.OK)
    public ClubResponse query(@PathVariable Long clubId) {
        return clubQueryService.execute(clubId);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Page<ClubSearchResponse> search(@RequestParam String keyword, Pageable pageable) {
        return clubSearchService.execute(keyword,pageable);
    }
}
