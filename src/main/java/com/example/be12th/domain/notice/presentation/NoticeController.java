package com.example.be12th.domain.notice.presentation;

import com.example.be12th.domain.notice.presentation.dto.request.NoticeRequest;
import com.example.be12th.domain.notice.presentation.dto.response.NoticeResponse;
import com.example.be12th.domain.notice.service.NoticeCreateService;
import com.example.be12th.domain.notice.service.NoticeQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeCreateService noticeCreateService;
    private final NoticeQueryService noticeQueryService;

    @PostMapping("/{recruitmentId}")
    public void create(@PathVariable Long recruitmentId, @Valid @RequestBody NoticeRequest request) {
        noticeCreateService.execute(recruitmentId, request);
    }

    @GetMapping("/{noticeId}")
    public NoticeResponse getNotice(@PathVariable Long noticeId) {
        return noticeQueryService.execute(noticeId);
    }
}
