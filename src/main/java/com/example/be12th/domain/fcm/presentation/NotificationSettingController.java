package com.example.be12th.domain.fcm.presentation;

import com.example.be12th.domain.fcm.presentation.dto.request.NotificationSettingRequest;
import com.example.be12th.domain.fcm.presentation.dto.response.NotificationSettingResponse;
import com.example.be12th.domain.fcm.service.NotificationSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications/settings")
@RequiredArgsConstructor
public class NotificationSettingController {

    private final NotificationSettingService notificationSettingService;

    @GetMapping
    public NotificationSettingResponse getMySetting() {
        return notificationSettingService.getMySetting();
    }

    @PatchMapping
    public NotificationSettingResponse update(@RequestBody NotificationSettingRequest request) {
        return notificationSettingService.update(request);
    }
}