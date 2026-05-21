package com.example.be12th.domain.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserNameRequest(
        @NotBlank
        @Size(max = 25)
        String username
) {
}
