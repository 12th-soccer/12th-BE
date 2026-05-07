package com.example.be12th.domain.fcm.service;

import com.example.be12th.domain.fcm.domain.FcmToken;
import com.example.be12th.domain.fcm.domain.repository.FcmRepository;
import com.example.be12th.domain.fcm.presentation.dto.request.FcmTokenRequest;
import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "firebase.enabled", havingValue = "true")
public class FcmTokenService {

    private final FcmRepository fcmRepository;
    private final UserRepository userRepository;
    private final UserFacade userFacade;

    @Transactional
    public void saveToken(FcmTokenRequest request) {
        if (request == null || request.getToken() == null || request.getToken().isBlank()) {
            throw new IllegalArgumentException("FCM 토큰이 비어 있습니다.");
        }

        User user = userRepository.findById(userFacade.currentUserId())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        String token = request.getToken().trim();

        boolean exists = fcmRepository.existsByUserIdAndToken(user.getId(), token);
        if (exists) {
            return;
        }

        fcmRepository.save(
                FcmToken.builder()
                        .user(user)
                        .token(token)
                        .build()
        );
    }
}
