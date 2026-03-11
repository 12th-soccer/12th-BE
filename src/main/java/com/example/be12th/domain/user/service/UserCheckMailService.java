package com.example.be12th.domain.user.service;

import com.example.be12th.domain.user.presentation.dto.request.EmailCheckRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCheckMailService {
    private final RedisTemplate<String, String> redisTemplate;

    public boolean execute(EmailCheckRequest emailCheckRequest) {

        String saveCode = redisTemplate.opsForValue().get(emailCheckRequest.getEmail());

        if(saveCode == null) {
            return false;
        }
        return saveCode.equals(emailCheckRequest.getCode());
    }
}
