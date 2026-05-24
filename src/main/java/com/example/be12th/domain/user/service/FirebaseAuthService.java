package com.example.be12th.domain.user.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import org.springframework.stereotype.Service;

@Service
public class FirebaseAuthService {

    public FirebaseToken verifyIdToken(String firebaseIdToken) {
        try {
            return FirebaseAuth.getInstance().verifyIdToken(firebaseIdToken);
        } catch (FirebaseAuthException e) {
            throw new IllegalArgumentException("유효하지 않은 Firebase 토큰입니다.");
        }
    }

    public UserRecord getUser(String uid) {
        try {
            return FirebaseAuth.getInstance().getUser(uid);
        } catch (FirebaseAuthException e) {
            throw new IllegalArgumentException("Firebase 유저를 찾을 수 없습니다.");
        }
    }
}
