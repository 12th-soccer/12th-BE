package com.example.be12th.domain.club.service;

import com.example.be12th.domain.club.domain.repository.ClubRepository;
import com.example.be12th.domain.club.presentation.dto.response.ClubSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClubSearchService {
    private final ClubRepository clubRepository;

    @Transactional
    public Page<ClubSearchResponse> execute(String keyword, Pageable pageable) {
        return clubRepository
                .findByClubNameContaining(keyword, pageable)
                .map(ClubSearchResponse::from);
    }
}
