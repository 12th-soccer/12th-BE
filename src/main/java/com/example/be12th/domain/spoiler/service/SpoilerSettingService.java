package com.example.be12th.domain.spoiler.service;

import com.example.be12th.domain.spoiler.domain.SpoilerSetting;
import com.example.be12th.domain.spoiler.domain.repository.SpoilerRepository;
import com.example.be12th.domain.spoiler.presentation.dto.Response.SpoilerResponse;
import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.facade.UserFacade;
import com.example.be12th.global.error.exception.App12thException;
import com.example.be12th.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SpoilerSettingService {
    private final SpoilerRepository spoilerRepository;
    private final UserRepository userRepository;
    private final UserFacade userFacade;

    @Transactional
    public SpoilerResponse execute(){
        User user = userRepository.findById(userFacade.currentUserId())
                .orElseThrow(() -> new App12thException(ErrorCode.USER_NOT_FOUND));

        SpoilerSetting spoilerSetting = spoilerRepository.findByUserId(user.getId())
                .orElseGet(() -> spoilerRepository.save(
                        SpoilerSetting.builder()
                                .user(user)
                                .spoilerEnabled(false)
                                .build()
                ));
     return SpoilerResponse.from(spoilerSetting);
    }
}
