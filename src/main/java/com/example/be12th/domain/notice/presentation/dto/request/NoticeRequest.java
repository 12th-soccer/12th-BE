package com.example.be12th.domain.notice.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NoticeRequest(
        @NotBlank
        @Size(max = 255)
        String description
) {
}
