package com.example.be12th.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSmsVerifyService {

    private final StringRedisTemplate redisTemplate;

    public void execute(String phone, String code) {
        String redisKey = "sms:" + phone;
        String savedCode = redisTemplate.opsForValue().get(redisKey);

        if (savedCode == null) {
            throw new RuntimeException("인증번호가 만료되었거나 존재하지 않습니다.");
        }

        if (!savedCode.equals(code)) {
            throw new RuntimeException("인증번호가 일치하지 않습니다.");
        }

        redisTemplate.delete(redisKey);
    }
}
