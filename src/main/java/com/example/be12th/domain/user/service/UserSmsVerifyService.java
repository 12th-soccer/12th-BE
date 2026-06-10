package com.example.be12th.domain.user.service;

import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserSmsVerifyService {

    private final StringRedisTemplate redisTemplate;
    private final UserRepository userRepository;
    private final UserFacade userFacade;

    @Transactional
    public void execute(String phone, String code) {
        String redisKey = "sms:" + phone;
        String savedCode = redisTemplate.opsForValue().get(redisKey);

        if (savedCode == null) {
            throw new RuntimeException("인증번호가 만료되었거나 존재하지 않습니다.");
        }

        if (!savedCode.equals(code)) {
            throw new RuntimeException("인증번호가 일치하지 않습니다.");
        }

        Long userId = userFacade.currentUserId();
        if (userRepository.existsByPhoneNumberAndIdNot(phone, userId)) {
            throw new RuntimeException("이미 사용 중인 전화번호입니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        user.updatePhoneNumber(phone);
        redisTemplate.delete(redisKey);
    }
}
