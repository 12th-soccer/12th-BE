package com.example.be12th.domain.notice.service;

import com.example.be12th.domain.join.domain.repository.JoinRepository;
import com.example.be12th.domain.notice.domain.repository.NoticeRepository;
import com.example.be12th.domain.notice.presentation.dto.request.NoticeRequest;
import com.example.be12th.domain.recruitment.domain.Recruitment;
import com.example.be12th.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeCreateService {
    private final NoticeRepository noticeRepository;
    private final JoinRepository joinRepository;

    boolean canAccessNoticeRoom(User user, Recruitment recruitment) {
        if (recruitment.getUser().getId().equals(user.getId())) {
            return true;
        }
        return joinRepository.existsByUserAndRecruitment(user, recruitment);
    }
    public void execute(NoticeRequest noticeRequest) {

    }
}
