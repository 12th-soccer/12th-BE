package com.example.be12th.domain.fcm.presentation;

import com.example.be12th.domain.fcm.presentation.dto.request.FcmTokenRequest;
import com.example.be12th.domain.fcm.service.FcmTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fcm/tokens")
@RequiredArgsConstructor
@ConditionalOnProperty(name = "firebase.enabled", havingValue = "true")
public class FcmTokenController {

    private final FcmTokenService fcmTokenService;

    @PostMapping
    public void saveToken(@RequestBody FcmTokenRequest request) {
        fcmTokenService.saveToken(request);
    }
}
