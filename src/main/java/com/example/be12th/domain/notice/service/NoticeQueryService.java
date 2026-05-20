package com.example.be12th.domain.notice.service;

import com.example.be12th.domain.join.domain.repository.JoinRepository;
import com.example.be12th.domain.notice.domain.Notice;
import com.example.be12th.domain.notice.domain.repository.NoticeRepository;
import com.example.be12th.domain.notice.presentation.dto.response.NoticeResponse;
import com.example.be12th.domain.recruitment.domain.Recruitment;
import com.example.be12th.domain.user.domain.User;
import com.example.be12th.domain.user.domain.repository.UserRepository;
import com.example.be12th.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoticeQueryService {
    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;
    private final JoinRepository joinRepository;
    private final UserFacade userFacade;

    private boolean canReadNotice(User user, Recruitment recruitment) {
        if (recruitment.getUser().getId().equals(user.getId())) {
            return true;
        }
        return joinRepository.existsByUserAndRecruitment(user, recruitment);
    }

    @Transactional(readOnly = true)
    public NoticeResponse execute(Long noticeId) {
        User user = userRepository.findById(userFacade.currentUserId())
                .orElseThrow(() -> new RuntimeException("해당 유저를 찾을수 없습니다."));

        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new RuntimeException("해당 공지를 찾을수없습니다,"));

        if (!canReadNotice(user, notice.getRecruitment())) {
            throw new RuntimeException("당신은 해당 모집게시물에 참여하지않으셨습니다.");
        }

        return NoticeResponse.from(notice);
    }
}
