package com.example.be12th.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UserSmsService {

    private final DefaultMessageService messageService;
    private final StringRedisTemplate redisTemplate;

    @Value("${solapi.sender}")
    private String fromNumber;

    public void sendSms(String toNumber) {
        validateSender();

        String code = createCode();

        Message message = new Message();
        message.setFrom(fromNumber);
        message.setTo(toNumber);
        message.setText("[BE12th] 인증번호는 " + code + "입니다.");

        try {
            messageService.send(message);
        } catch (NurigoMessageNotReceivedException e) {
            log.warn("SMS was not received. from={}, to={}, failedMessages={}",
                    maskPhone(fromNumber), maskPhone(toNumber), e.getFailedMessageList(), e);
            throw new RuntimeException("문자 발송에 실패했습니다. Solapi 설정과 발신번호 승인 상태를 확인해주세요.", e);
        } catch (NurigoEmptyResponseException e) {
            log.warn("Solapi returned empty response. from={}, to={}",
                    maskPhone(fromNumber), maskPhone(toNumber), e);
            throw new RuntimeException("솔라피 응답이 비어있습니다.", e);
        } catch (NurigoUnknownException e) {
            log.warn("Unknown Solapi SMS error. from={}, to={}, solapiMessage={}",
                    maskPhone(fromNumber), maskPhone(toNumber), e.getMessage(), e);
            throw new RuntimeException("문자 발송 중 오류가 발생했습니다: " + e.getMessage(), e);
        } catch (Exception e) {
            log.warn("Unexpected SMS send error. from={}, to={}",
                    maskPhone(fromNumber), maskPhone(toNumber), e);
            throw new RuntimeException("문자 발송 중 예상하지 못한 오류가 발생했습니다: " + e.getMessage(), e);
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

    private void validateSender() {
        if (fromNumber == null || fromNumber.isBlank()) {
            throw new RuntimeException("Solapi 발신번호가 설정되지 않았습니다.");
        }
    }

    private String maskPhone(String phone) {
        if (phone == null || phone.length() < 7) {
            return "****";
        }
        return phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4);
    }
}
