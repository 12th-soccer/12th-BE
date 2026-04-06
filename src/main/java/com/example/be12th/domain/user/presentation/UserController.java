package com.example.be12th.domain.user.presentation;

import com.example.be12th.domain.user.presentation.dto.request.EmailCheckRequest;
import com.example.be12th.domain.user.presentation.dto.request.EmailRequest;
import com.example.be12th.domain.user.presentation.dto.request.UserRequest;
import com.example.be12th.domain.user.presentation.dto.response.LoginResponse;
import com.example.be12th.domain.user.service.UserCheckMailSignupService;
import com.example.be12th.domain.user.service.UserLoginService;
import com.example.be12th.domain.user.service.UserLogoutService;
import com.example.be12th.domain.user.service.UserMailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserCheckMailSignupService userCheckMailService;
    private final UserMailService userMailService;
    private final UserLoginService userLoginService;
    private final UserLogoutService userLogoutService;

    @PostMapping("/email")
    @ResponseStatus(HttpStatus.OK)
    public void sendEmail(@RequestBody EmailRequest emailRequest) {
        userMailService.execute(emailRequest);
    }

    @PostMapping("/verify/signup")
    @ResponseStatus(HttpStatus.OK)
    public boolean verifyEmail(@RequestBody @Valid EmailCheckRequest emailCheckRequest) {
        return userCheckMailService.execute(emailCheckRequest);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody UserRequest userRequest) {
        return userLoginService.execute(userRequest);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(HttpServletRequest httpServletRequest) {
        userLogoutService.logout(httpServletRequest);
    }
}