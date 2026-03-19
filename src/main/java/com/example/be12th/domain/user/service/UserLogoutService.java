package com.example.be12th.domain.user.service;

import com.example.be12th.global.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserLogoutService {
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
    public void logout(HttpServletRequest httpServletRequest) {
        String accessToken = jwtTokenProvider.resolveToken(httpServletRequest);// HttpServletRequest 에서 받아온 클라이언트 정보중 토큰만 추출

        if(accessToken != null || jwtTokenProvider.validateToken(accessToken)) {
            String email = jwtTokenProvider.getEmailFromToken(accessToken);
            redisTemplate.delete(email);
        }
    }
}
