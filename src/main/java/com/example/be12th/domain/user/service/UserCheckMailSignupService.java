package com.example.be12th.domain.user.service;

import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.presentation.dto.request.EmailCheckSignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCheckMailService {
    private final RedisTemplate<String, String> redisTemplate;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean execute(EmailCheckSignupRequest emailCheckRequest) {

        String saveCode = redisTemplate.opsForValue().get(emailCheckRequest.getEmail());

        if (saveCode == null) return false;

        if (saveCode.equals(emailCheckRequest.getCode())) {
            userRepository.save(
                    User.builder()
                            .email(emailCheckRequest.getEmail())
                            .password(passwordEncoder.encode(emailCheckRequest.getPassword()))
                            .build()
            );
            redisTemplate.delete(emailCheckRequest.getEmail());
        }
        return true;
    }
}
