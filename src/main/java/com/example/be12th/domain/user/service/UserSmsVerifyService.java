package com.example.be12th.domain.user.service;

import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.facade.UserFacade;
import com.example.be12th.global.error.exception.App12thException;
import com.example.be12th.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserSmsVerifyService {

    private final StringRedisTemplate redisTemplate;
    private final UserRepository userRepository;
    private final UserFacade userFacade;

    @Transactional
    public void execute(String phone, String code) {
        String redisKey = "sms:" + phone;
        String savedCode = redisTemplate.opsForValue().get(redisKey);

        if (savedCode == null) {
            throw new App12thException(ErrorCode.SMS_CODE_EXPIRED);
        }

        if (!savedCode.equals(code)) {
            throw new App12thException(ErrorCode.SMS_CODE_MISMATCH);
        }

        Long userId = userFacade.currentUserId();
        if (userRepository.existsByPhoneNumberAndIdNot(phone, userId)) {
            throw new App12thException(ErrorCode.PHONE_NUMBER_ALREADY_EXISTS);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new App12thException(ErrorCode.USER_NOT_FOUND));
        user.updatePhoneNumber(phone);
        redisTemplate.delete(redisKey);
    }
}
