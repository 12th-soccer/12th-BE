package com.example.be12th.domain.user.service;

import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.presentation.dto.response.UserResponse;
import com.example.be12th.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public UserResponse execute(String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        jwtTokenProvider.validateToken(token);

        String email = jwtTokenProvider.getEmailFromToken(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("현재 로그인한 사용자와 일치하지않음."));

        return UserResponse.from(user);
    }
}
