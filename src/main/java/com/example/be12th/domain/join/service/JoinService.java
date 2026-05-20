package com.example.be12th.domain.join.service;

import com.example.be12th.domain.join.domain.Join;
import com.example.be12th.domain.join.domain.repository.JoinRepository;
import com.example.be12th.domain.recruitment.domain.Recruitment;
import com.example.be12th.domain.recruitment.domain.repository.RecruitmentRepository;
import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.facade.UserFacade;
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
                .orElseThrow(() -> new RuntimeException ("해당 유저를 찾을수 없습니다"));

        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(()-> new RuntimeException("해당 모집 게시물을 찾을수없습니다."));

        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        if (!now.isBefore(recruitment.getExpiredAt())) {
            throw new RuntimeException("마감된 모집글입니다.");
        }

        boolean existsRecruitmentAndUser = joinRepository.existsByUserAndRecruitment(user, recruitment);
        if (existsRecruitmentAndUser) {
            throw new RuntimeException("이미 가입 신청한 그룹입니다.");
        }

        long joinCount = joinRepository.countByRecruitment(recruitment);
        if (joinCount >= recruitment.getHeadCount()) {
            throw new RuntimeException("모집 인원이 가득 찼습니다.");
        }

        Join join = Join.builder()
                .user(user)
                .recruitment(recruitment)
                .build();
        joinRepository.save(join);
    }
}
