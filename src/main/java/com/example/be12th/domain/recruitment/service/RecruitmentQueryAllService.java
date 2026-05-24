package com.example.be12th.domain.recruitment.service;

import com.example.be12th.domain.join.domain.repository.JoinRepository;
import com.example.be12th.domain.recruitment.domain.repository.RecruitmentRepository;
import com.example.be12th.domain.recruitment.presentation.dto.response.RecruitmentResponse;
import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecruitmentQueryAllService {
    private final RecruitmentRepository recruitmentRepository;
    private final UserRepository userRepository;
    private final UserFacade userFacade;
    private final JoinRepository joinRepository;

    @Transactional(readOnly = true)
    public Page<RecruitmentResponse> execute(Pageable pageable) {
        Long currentId = userFacade.currentUserId();

        User user = userRepository.findById(currentId)
                .orElseThrow(() -> new RuntimeException("해당 유저를 찾을수 없습니다."));

        if (!user.isPhoneVerified()) {
            throw new IllegalArgumentException("휴대전화번호 인증이 필요합니다.");
        }

        return recruitmentRepository.findAll(pageable)
                .map(recruitment -> RecruitmentResponse.from(
                        recruitment,
                        Math.toIntExact(joinRepository.countByRecruitment(recruitment) + 1)
                ));
    }
}
