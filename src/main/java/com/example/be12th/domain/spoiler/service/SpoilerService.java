package com.example.be12th.domain.spoiler.service;

import com.example.be12th.domain.match.presentation.dto.response.MatchResponse;
import com.example.be12th.domain.spoiler.domain.SpoilerSetting;
import com.example.be12th.domain.spoiler.domain.repository.SpoilerRepository;
import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class SpoilerService {
    private final SpoilerRepository spoilerRepository;
    private final UserRepository userRepository;
    private final UserFacade userFacade;

    public boolean isTodayMatch(String matchDate) {
        LocalDate matchLocalDate = OffsetDateTime.parse(matchDate)
                .atZoneSameInstant(ZoneId.of("Asia/Seoul"))
                .toLocalDate();

        return matchLocalDate.equals(LocalDate.now(ZoneId.of("Asia/Seoul")));
    }

    public boolean isSpoilerEnabled() {
        User user = userRepository.findById(userFacade.currentUserId())
                .orElseThrow(() -> new RuntimeException("유저를 찾을수 없습니다."));


        SpoilerSetting spoilerSetting = spoilerRepository.findByUserId(user.getId())
                .orElseGet(() -> spoilerRepository.save(
                        SpoilerSetting.builder()
                                .user(user)
                                .spoilerEnabled(true)
                                .build()
                ));

        return Boolean.TRUE.equals(spoilerSetting.getSpoilerEnabled());
    }
}
