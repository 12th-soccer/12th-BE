package com.example.be12th.domain.join.service;

import com.example.be12th.domain.join.domain.repository.JoinRepository;
import com.example.be12th.domain.join.presentation.dto.response.JoinResponse;
import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JoinQueryAllService {
    private final JoinRepository joinRepository;
    private final UserRepository userRepository;
    private final UserFacade userFacade;

    @Transactional(readOnly = true)
    public List<JoinResponse> execute() {
        User user = userRepository.findById(userFacade.currentUserId())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        return joinRepository.findByUser(user).stream()
                .map(join -> JoinResponse.from(
                        join,
                        Math.toIntExact(joinRepository.countByRecruitment(join.getRecruitment()) + 1)
                ))
                .toList();
    }
}
