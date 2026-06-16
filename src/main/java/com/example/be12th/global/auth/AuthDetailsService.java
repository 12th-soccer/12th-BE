package com.example.be12th.global.auth;

import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.global.error.exception.App12thException;
import com.example.be12th.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String accountId) {
        return userRepository.findByEmail(accountId)
                .filter(user -> !user.isDeleted())
                .map(AuthDetails::new)
                .orElseThrow(() -> new App12thException(ErrorCode.USER_NOT_FOUND));
    }
}
