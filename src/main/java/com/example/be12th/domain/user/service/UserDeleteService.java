package com.example.be12th.domain.user.service;

import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.facade.UserFacade;
import com.example.be12th.global.error.exception.App12thException;
import com.example.be12th.global.error.exception.ErrorCode;
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
                .orElseThrow(() -> new App12thException(ErrorCode.USER_NOT_FOUND));

        if (user.isDeleted()) {
            throw new App12thException(ErrorCode.ALREADY_DELETED_USER);
        }

        user.withdraw();
    }
}
