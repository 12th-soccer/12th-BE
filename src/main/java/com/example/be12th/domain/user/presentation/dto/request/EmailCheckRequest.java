package com.example.be12th.domain.user.presentation.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmailCheckRequest {
    @NotBlank(message = "이메일은 필수 입력 값 입니다.")
    @Email(message = "이메일 형식으로 작성해주세요.")
    private String email;

    private String code;

    @NotBlank(message = "비밀번호에 공백,띄어쓰기를 포함하실수없습니다.")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$*]{8,255}$", message = "최소 8자 ~ 최대 255자까지 가능하며, 숫자, 영어 대소문자와 _!#$*만 허용됩니다")
    private String password;
}
