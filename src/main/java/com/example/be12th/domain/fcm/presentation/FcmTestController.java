package com.example.be12th.domain.fcm.presentation;

import com.example.be12th.domain.fcm.domain.repository.FcmRepository;
import com.example.be12th.domain.fcm.service.FcmService;
import com.example.be12th.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fcm/test")
@RequiredArgsConstructor
public class FcmTestController {

    private final FcmRepository fcmRepository;
    private final FcmService fcmService;
    private final UserFacade userFacade;

    @PostMapping
    public void test() {
        Long userId = userFacade.currentUserId();
        List<String> tokens = fcmRepository.findTokensByUserId(userId);

        for (String token : tokens) {
            fcmService.sendMessage(token, "테스트 알림", "FCM 테스트입니다.");
        }
    }
}