package com.example.be12th.domain.fcm.presentation;

import com.example.be12th.domain.fcm.presentation.dto.request.NotificationSettingRequest;
import com.example.be12th.domain.fcm.presentation.dto.response.NotificationSettingResponse;
import com.example.be12th.domain.fcm.service.NotificationSettingService;
import com.example.be12th.domain.fcm.service.NotificationUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications/settings")
@RequiredArgsConstructor
@ConditionalOnProperty(name = "firebase.enabled", havingValue = "true")
public class NotificationSettingController {

    private final NotificationSettingService notificationSettingService;
    private final NotificationUpdateService notificationUpdateService;

    @GetMapping
    public NotificationSettingResponse getMySetting() {
        return notificationSettingService.execute();
    }

    @PatchMapping
    public NotificationSettingResponse update(@RequestBody NotificationSettingRequest request) {
        return notificationUpdateService.execute(request);
    }
}
