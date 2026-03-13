package com.example.be12th.domain.user.service;

import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.presentation.dto.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSignupServicde {
    private final RedisTemplate<String, String> redisTemplate;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void execute(UserRequest userRequest) {

        String verified = redisTemplate.opsForValue().get(userRequest.getEmail()+"verified");

        if (verified == null) {
            throw new RuntimeException("이메일 인증을 확인해주세요.");
        }
        userRepository.save(
                User.builder()
                        .email(userRequest.getEmail())
                        .password(passwordEncoder.encode(userRequest.getPassword()))
                        .build()
        );
    }
}
