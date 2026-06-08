package com.example.be12th.domain.chat.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChatMessageRequest(
        @NotBlank
        @Size(max = 500)
        String content
) {
}
