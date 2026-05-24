package com.example.be12th.domain.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PhoneVerificationRequest(
        @NotBlank
        String firebaseIdToken
) {
}
