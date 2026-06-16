package com.example.be12th.domain.user.service;

import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.presentation.dto.response.UserResponse;
import com.example.be12th.global.error.exception.App12thException;
import com.example.be12th.global.error.exception.ErrorCode;
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
                .orElseThrow(() -> new App12thException(ErrorCode.USER_NOT_FOUND));

        if (user.isDeleted()) {
            throw new App12thException(ErrorCode.DELETED_USER);
        }

        return UserResponse.from(user);
    }
}
