package com.example.be12th.domain.user.service;

import com.example.be12th.global.error.exception.App12thException;
import com.example.be12th.global.error.exception.ErrorCode;
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
            throw new App12thException(ErrorCode.SMS_SEND_FAILED, e);
        } catch (NurigoEmptyResponseException e) {
            log.warn("Solapi returned empty response. from={}, to={}",
                    maskPhone(fromNumber), maskPhone(toNumber), e);
            throw new App12thException(ErrorCode.SMS_EMPTY_RESPONSE, e);
        } catch (NurigoUnknownException e) {
            log.warn("Unknown Solapi SMS error. from={}, to={}, solapiMessage={}",
                    maskPhone(fromNumber), maskPhone(toNumber), e.getMessage(), e);
            throw new App12thException(ErrorCode.SMS_UNKNOWN_ERROR, e);
        } catch (Exception e) {
            log.warn("Unexpected SMS send error. from={}, to={}",
                    maskPhone(fromNumber), maskPhone(toNumber), e);
            throw new App12thException(ErrorCode.SMS_UNEXPECTED_ERROR, e);
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
            throw new App12thException(ErrorCode.SMS_SENDER_NOT_CONFIGURED);
        }
    }

    private String maskPhone(String phone) {
        if (phone == null || phone.length() < 7) {
            return "****";
        }
        return phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4);
    }
}
