package com.example.be12th.domain.user.service;

import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.presentation.dto.request.UserRequest;
import com.example.be12th.domain.user.presentation.dto.response.LoginResponse;
import com.example.be12th.global.error.exception.App12thException;
import com.example.be12th.global.error.exception.ErrorCode;
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
                .orElseThrow(() -> new App12thException(ErrorCode.USER_NOT_FOUND));

        if (user.isDeleted()) {
            throw new App12thException(ErrorCode.DELETED_USER);
        }

        if(!passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
            throw new App12thException(ErrorCode.INVALID_PASSWORD);
        }

        String accessToken =
                jwtTokenProvider.generateAccessToken(user.getEmail());
        String refreshToken =
                jwtTokenProvider.generateRefreshToken(user.getEmail());

        return new LoginResponse(accessToken, refreshToken);
    }
}
