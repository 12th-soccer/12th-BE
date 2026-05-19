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

@Service
@RequiredArgsConstructor
public class JoinService {
    private final JoinRepository joinRepository;
    private final UserRepository userRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final UserFacade userFacade;

    public void execute(Long recruitmentId){
        User user = userRepository.findById(userFacade.currentUserId())
                .orElseThrow(() -> new RuntimeException ("해당 유저를 찾을수 없습니다"));

        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(()-> new RuntimeException("해당 모집 게시물을 찾을수없습니다."));

    final boolean ExistsRecruitmentAndUser = joinRepository.existsByUserAndRecruitment(user, recruitment);

    if(ExistsRecruitmentAndUser){
        throw new RuntimeException("이미 가입 신청한 그룹입니다.");
    }
    Join join = Join.builder()
            .user(user)
            .recruitment(recruitment)
            .build();
    joinRepository.save(join);
    }
}
