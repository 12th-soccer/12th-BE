package com.example.be12th.domain.user.service;

import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.presentation.dto.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSignupServicde {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void execute(UserRequest userRequest) {
        userRepository.save(
                User.builder()
                        .email(userRequest.getEmail())
                        .password(passwordEncoder.encode(userRequest.getPassword()))
                        .build()
        );
    }
}
