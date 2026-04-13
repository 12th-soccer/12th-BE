package com.example.be12th.domain.fcm.service;

import com.example.be12th.domain.fcm.domain.NotificationSetting;
import com.example.be12th.domain.fcm.domain.repository.NotificationSettingRepository;
import com.example.be12th.domain.fcm.presentation.dto.request.NotificationSettingRequest;
import com.example.be12th.domain.fcm.presentation.dto.response.NotificationSettingResponse;
import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationSettingService {

    private final NotificationSettingRepository notificationSettingRepository;
    private final UserRepository userRepository;
    private final UserFacade userFacade;

    @Transactional
    public NotificationSettingResponse update(NotificationSettingRequest request) {
        User user = userRepository.findById(userFacade.currentUserId())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        NotificationSetting setting = notificationSettingRepository.findByUserId(user.getId())
                .orElseGet(() -> notificationSettingRepository.save(
                        NotificationSetting.builder()
                                .user(user)
                                .notificationEnabled(true)
                                .oneHourBeforeEnabled(true)
                                .thirtyMinutesBeforeEnabled(true)
                                .fifteenMinutesBeforeEnabled(true)
                                .matchStartEnabled(true)
                                .build()
                ));

        setting.update(
                request.getNotificationEnabled(),
                request.getOneHourBeforeEnabled(),
                request.getThirtyMinutesBeforeEnabled(),
                request.getFifteenMinutesBeforeEnabled(),
                request.getMatchStartEnabled()
        );

        return NotificationSettingResponse.from(setting);
    }

    @Transactional
    public NotificationSettingResponse getMySetting() {
        User user = userRepository.findById(userFacade.currentUserId())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        NotificationSetting setting = notificationSettingRepository.findByUserId(user.getId())
                .orElseGet(() -> notificationSettingRepository.save(
                        NotificationSetting.builder()
                                .user(user)
                                .notificationEnabled(true)
                                .oneHourBeforeEnabled(true)
                                .thirtyMinutesBeforeEnabled(true)
                                .fifteenMinutesBeforeEnabled(true)
                                .matchStartEnabled(true)
                                .build()
                ));

        return NotificationSettingResponse.from(setting);
    }
}
