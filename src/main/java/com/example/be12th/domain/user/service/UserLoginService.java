package com.example.be12th.domain.user.service;

import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.presentation.dto.request.UserRequest;
import com.example.be12th.domain.user.presentation.dto.response.LoginResponse;
import com.example.be12th.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserLoginService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public LoginResponse execute(UserRequest userRequest) {
        User user = userRepository.findByEmail(userRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("유저를 찾을수없다"));

        if(!passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀빈호가 일치하지않습니다.");
        }

        String accessToken =
                jwtTokenProvider.generateAccessToken(user.getEmail());
        String refreshToken =
                jwtTokenProvider.generateRefreshToken(user.getEmail());

        return new LoginResponse(accessToken, refreshToken);
    }
}
