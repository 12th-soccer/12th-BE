package com.example.be12th.domain.fcm.service;

import com.example.be12th.global.error.exception.App12thException;
import com.example.be12th.global.error.exception.ErrorCode;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "firebase.enabled", havingValue = "true")
public class FcmService {
    public void sendMessage(String token, String title, String body) {
        try {
            Message message = Message.builder()
                    .setToken(token)
                    .setNotification(
                            Notification.builder()
                                    .setTitle(title)
                                    .setBody(body)
                                    .build()
                    )
                    .build();

            FirebaseMessaging.getInstance().send(message);
        } catch (Exception e) {
            throw new App12thException(ErrorCode.FCM_SEND_FAILED, e);
        }
    }
}
