package com.example.be12th.domain.user.facade;

import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.global.error.exception.App12thException;
import com.example.be12th.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade{
    private final UserRepository userRepository;

    public Long currentUserId() {
        String accountId = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByEmail(accountId)
                .filter(user -> !user.isDeleted())
                .orElseThrow(() -> new App12thException(ErrorCode.USER_NOT_FOUND)).getId();
    }
}
