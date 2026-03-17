package com.example.be12th.domain.user.service;

import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.presentation.dto.request.EmailCheckRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCheckMailSignupService {
    private final RedisTemplate<String, String> redisTemplate;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Validates a stored email verification code and creates a new user when the provided code matches.
     *
     * @param emailCheckRequest request containing the email to look up, the verification code to compare, and the plain-text password to store if verification succeeds
     * @return `true` if a verification code was present in Redis for the given email (a user is created when the codes match), `false` if no code was stored for the email
     */
    public boolean execute(EmailCheckRequest emailCheckRequest) {

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
