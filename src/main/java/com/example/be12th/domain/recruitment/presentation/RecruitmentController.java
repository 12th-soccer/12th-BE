package com.example.be12th.domain.recruitment.presentation;

import com.example.be12th.domain.recruitment.presentation.dto.request.RecruitmentRequest;
import com.example.be12th.domain.recruitment.presentation.dto.response.RecruitmentResponse;
import com.example.be12th.domain.recruitment.service.RecruitmentCreateService;
import com.example.be12th.domain.recruitment.service.RecruitmentQueryAllService;
import com.example.be12th.domain.recruitment.service.RecruitmentQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recruitment")
public class RecruitmentController {
    private final RecruitmentCreateService recruitmentCreateService;
    private final RecruitmentQueryService recruitmentQueryService;
    private final RecruitmentQueryAllService recruitmentQueryAllService;

    @PostMapping
    public void create(@Valid @RequestBody RecruitmentRequest request) {
        recruitmentCreateService.execute(request);
    }

    @GetMapping("/{recruitmentId}")
    public RecruitmentResponse getRecruitment(@PathVariable Long recruitmentId) {
        return recruitmentQueryService.execute(recruitmentId);
    }

    @GetMapping
    public Page<RecruitmentResponse> getRecruitments(Pageable pageable) {
        return recruitmentQueryAllService.execute(pageable);
    }
}
