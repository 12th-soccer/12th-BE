package com.example.be12th.domain.user.facade;

import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade{
    private final UserRepository userRepository;

    public Long currentUserId() {
        String accountId = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByEmail(accountId)
                .orElseThrow(() -> new UsernameNotFoundException("현재 유저를 찾을수없습니다.")).getId();
    }
}

