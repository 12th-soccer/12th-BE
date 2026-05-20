package com.example.be12th.domain.user.service;

import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDeleteService {
    private final UserRepository userRepository;
    private final UserFacade userFacade;

    @Transactional
    public void execute() {
        User user = userRepository.findById(userFacade.currentUserId())
                .orElseThrow(() -> new RuntimeException("해당 유저를 찾을수 없습니다."));

        if (user.isDeleted()) {
            throw new RuntimeException("이미 탈퇴한 회원입니다.");
        }

        user.withdraw();
    }
}
