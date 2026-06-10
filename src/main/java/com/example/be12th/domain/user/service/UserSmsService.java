package com.example.be12th.domain.user.service;

import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.exception.NurigoEmptyResponseException;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.exception.NurigoUnknownException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserSmsService {

    private final DefaultMessageService messageService;
    private final StringRedisTemplate redisTemplate;

    @Value("${SOLAPI_SENDER}")
    private String fromNumber;

    public void sendSms(String toNumber) {
        String code = createCode();

        Message message = new Message();
        message.setFrom(fromNumber);
        message.setTo(toNumber);
        message.setText("[BE12th] 인증번호는 " + code + "입니다.");

        try {
            messageService.send(message);
        } catch (NurigoMessageNotReceivedException e) {
            throw new RuntimeException("문자 발송에 실패했습니다.");
        } catch (NurigoEmptyResponseException e) {
            throw new RuntimeException("솔라피 응답이 비어있습니다.");
        } catch (NurigoUnknownException e) {
            throw new RuntimeException("문자 발송 중 알 수 없는 오류가 발생했습니다.");
        }

        redisTemplate.opsForValue().set(
                "sms:" + toNumber,
                code,
                Duration.ofMinutes(3)
        );
    }

    private String createCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}
