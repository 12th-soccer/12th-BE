package com.example.be12th.domain.user.service;

import com.example.be12th.global.error.exception.App12thException;
import com.example.be12th.global.error.exception.ErrorCode;
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
            throw new App12thException(ErrorCode.INVALID_FIREBASE_TOKEN);
        }
    }

    public UserRecord getUser(String uid) {
        try {
            return FirebaseAuth.getInstance().getUser(uid);
        } catch (FirebaseAuthException e) {
            throw new App12thException(ErrorCode.FIREBASE_USER_NOT_FOUND);
        }
    }
}
