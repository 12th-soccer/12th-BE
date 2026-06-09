package com.example.be12th.domain.chat.presentation;

import com.example.be12th.domain.chat.presentation.dto.response.ChatMessageResponse;
import com.example.be12th.domain.chat.service.ChatMessageQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final ChatMessageQueryService chatMessageQueryService;

    @ResponseBody
    @GetMapping("/{matchId}/messages")
    public Slice<ChatMessageResponse> getMessages(@PathVariable Long matchId, Pageable pageable) {
        return chatMessageQueryService.execute(matchId, pageable);
    }
}
