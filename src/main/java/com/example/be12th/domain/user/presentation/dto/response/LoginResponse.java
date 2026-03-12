package com.example.be12th.domain.user.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class LoginResponse {
    private String AccessToken;
    private String RefreshToken;
}
