package com.example.be12th.domain.spoiler.service;

import com.example.be12th.domain.spoiler.domain.SpoilerSetting;
import com.example.be12th.domain.spoiler.domain.repository.SpoilerRepository;
import com.example.be12th.domain.spoiler.presentation.dto.Request.SpoilerRequest;
import com.example.be12th.domain.spoiler.presentation.dto.Response.SpoilerResponse;
import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SpoilerUpdateService {
    private final SpoilerRepository spoilerRepository;
    private final UserRepository userRepository;
    private final UserFacade userFacade;

    @Transactional
    public SpoilerResponse execute(SpoilerRequest spoilerRequest) {
        User user = userRepository.findById(userFacade.currentUserId())
                .orElseThrow(()-> new RuntimeException("유저를 찾을수 없습니다."));

        SpoilerSetting spoilerSetting = spoilerRepository.findById(user.getId())
                .orElseGet(() -> spoilerRepository.save(
                        SpoilerSetting.builder()
                                .user(user)
                                .spoilerEnabled(true)
                                .build())
                );
        spoilerSetting.update(
                spoilerRequest.getSpoilerEnabled()
        );
        return SpoilerResponse.from(spoilerSetting);
    }
}
