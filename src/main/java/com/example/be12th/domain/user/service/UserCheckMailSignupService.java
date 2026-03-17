package com.example.be12th.domain.user.service;

import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.presentation.dto.request.EmailCheckRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCheckMailSignupService {
    private final RedisTemplate<String, String> redisTemplate;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public boolean execute(EmailCheckRequest emailCheckRequest) {

        String saveCode = redisTemplate.opsForValue().get(emailCheckRequest.getEmail());

        if (saveCode == null) return false;

        if (saveCode.equals(emailCheckRequest.getCode())) {

            if(userRepository.existsByEmail(emailCheckRequest.getEmail())) {
                redisTemplate.delete(emailCheckRequest.getEmail());
                return false;
            }

            userRepository.save(
                    User.builder()
                            .email(emailCheckRequest.getEmail())
                            .password(passwordEncoder.encode(emailCheckRequest.getPassword()))
                            .build()
            );
            redisTemplate.delete(emailCheckRequest.getEmail());
            return true;
        }
        return false;
    }
}
