package com.example.be12th.domain.user.presentation.dto.request;

import lombok.Getter;

@Getter
public class EmailCheckRequest {
    private String email;
    private String code;
}
