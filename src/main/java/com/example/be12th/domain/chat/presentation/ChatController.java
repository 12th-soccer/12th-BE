package com.example.be12th.domain.chat.presentation;

import com.example.be12th.domain.chat.presentation.dto.response.ChatMessageResponse;
import com.example.be12th.domain.chat.service.ChatMessageQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final ChatMessageQueryService chatMessageQueryService;

    @ResponseBody
    @GetMapping("/{matchId}/messages")
    public List<ChatMessageResponse> getMessages(@PathVariable Long matchId) {
        return chatMessageQueryService.execute(matchId);
    }
}
