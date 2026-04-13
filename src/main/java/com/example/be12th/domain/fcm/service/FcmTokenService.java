package com.example.be12th.domain.fcm.service;

import com.example.be12th.domain.fcm.domain.FcmToken;
import com.example.be12th.domain.fcm.domain.repository.FcmRepository;
import com.example.be12th.domain.fcm.presentation.dto.request.FcmTokenRequest;
import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FcmTokenService {

    private final FcmRepository fcmRepository;
    private final UserRepository userRepository;
    private final UserFacade userFacade;

    @Transactional
    public void saveToken(FcmTokenRequest request) {
        User user = userRepository.findById(userFacade.currentUserId())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        boolean exists = fcmRepository.existsByUserIdAndToken(user.getId(), request.getToken());
        if (exists) {
            return;
        }

        fcmRepository.save(
                FcmToken.builder()
                        .user(user)
                        .token(request.getToken())
                        .build()
        );
    }
}