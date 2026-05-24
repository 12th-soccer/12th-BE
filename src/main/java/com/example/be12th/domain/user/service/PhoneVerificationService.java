package com.example.be12th.domain.user.service;

import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.facade.UserFacade;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PhoneVerificationService {

    private final FirebaseAuthService firebaseAuthService;
    private final UserRepository userRepository;
    private final UserFacade userFacade;

    @Transactional
    public void confirmPhoneVerification(String firebaseIdToken) {
        FirebaseToken decodedToken =
                firebaseAuthService.verifyIdToken(firebaseIdToken);

        UserRecord firebaseUser =
                firebaseAuthService.getUser(decodedToken.getUid());

        String phoneNumber = firebaseUser.getPhoneNumber();

        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalStateException("전화번호 인증이 완료되지 않았습니다.");
        }

        Long currentUserId = userFacade.currentUserId();

        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        if (userRepository.existsByPhoneNumberAndIdNot(phoneNumber, user.getId())) {
            throw new IllegalArgumentException("이미 사용 중인 전화번호입니다.");
        }

        user.updatePhoneNumber(phoneNumber);
    }
}
