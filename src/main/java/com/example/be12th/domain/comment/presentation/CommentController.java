package com.example.be12th.domain.comment.presentation;

import com.example.be12th.domain.comment.presentation.dto.request.CommentRequest;
import com.example.be12th.domain.comment.presentation.dto.response.CommentResponse;
import com.example.be12th.domain.comment.service.CommentCreateService;
import com.example.be12th.domain.comment.service.CommentQueryAllService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentCreateService commentCreateService;
    private final CommentQueryAllService commentQueryAllService;

    @PostMapping("/{noticeId}")
    public void create(@PathVariable Long noticeId, @Valid @RequestBody CommentRequest request) {
        commentCreateService.execute(noticeId, request);
    }

    @GetMapping("/{noticeId}")
    public List<CommentResponse> getComments(@PathVariable Long noticeId) {
        return commentQueryAllService.execute(noticeId);
    }
}
