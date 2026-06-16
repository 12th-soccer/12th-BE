package com.example.be12th.domain.join.service;

import com.example.be12th.domain.join.domain.Join;
import com.example.be12th.domain.join.domain.repository.JoinRepository;
import com.example.be12th.domain.recruitment.domain.Recruitment;
import com.example.be12th.domain.recruitment.domain.repository.RecruitmentRepository;
import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.facade.UserFacade;
import com.example.be12th.global.error.exception.App12thException;
import com.example.be12th.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class JoinService {
    private final JoinRepository joinRepository;
    private final UserRepository userRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final UserFacade userFacade;

    @Transactional
    public void execute(Long recruitmentId){
        User user = userRepository.findById(userFacade.currentUserId())
                .orElseThrow(() -> new App12thException(ErrorCode.USER_NOT_FOUND));

        Recruitment recruitment = recruitmentRepository.findByIdForUpdate(recruitmentId)
                .orElseThrow(() -> new App12thException(ErrorCode.RECRUITMENT_NOT_FOUND));

        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        if (!now.isBefore(recruitment.getExpiredAt())) {
            throw new App12thException(ErrorCode.RECRUITMENT_EXPIRED);
        }

        boolean existsRecruitmentAndUser = joinRepository.existsByUserAndRecruitment(user, recruitment);
        if (existsRecruitmentAndUser) {
            throw new App12thException(ErrorCode.ALREADY_JOINED_RECRUITMENT);
        }

        long joinCount = joinRepository.countByRecruitment(recruitment);
        if (joinCount >= recruitment.getHeadCount()) {
            throw new App12thException(ErrorCode.RECRUITMENT_FULL);
        }

        Join join = Join.builder()
                .user(user)
                .recruitment(recruitment)
                .build();
        joinRepository.save(join);
    }
}
