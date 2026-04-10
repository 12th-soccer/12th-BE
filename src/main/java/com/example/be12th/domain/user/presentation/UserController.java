package com.example.be12th.domain.user.presentation;

import com.example.be12th.domain.user.presentation.dto.request.EmailCheckRequest;
import com.example.be12th.domain.user.presentation.dto.request.EmailRequest;
import com.example.be12th.domain.user.presentation.dto.request.UserRequest;
import com.example.be12th.domain.user.presentation.dto.response.LoginResponse;
import com.example.be12th.domain.user.presentation.dto.response.UserResponse;
import com.example.be12th.domain.user.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserCheckMailSignupService userCheckMailService;
    private final UserMailService userMailService;
    private final UserLoginService userLoginService;
    private final UserLogoutService userLogoutService;
    private final UserInfoService userInfoService;

    @PostMapping("/email")
    @ResponseStatus(HttpStatus.OK)
    public void sendEmail(@RequestBody EmailRequest emailRequest) {
        userMailService.execute(emailRequest);
    }

    @GetMapping("/info")
<<<<<<< HEAD
    @ResponseStatus(HttpStatus.OK)
=======
>>>>>>> origin/refactor/12th-code
    public UserResponse getUserInfo(@RequestHeader("Authorization") String authorizationHeader) {
        return userInfoService.execute(authorizationHeader);
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