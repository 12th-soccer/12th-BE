package com.example.be12th.domain.user.presentation;

import com.example.be12th.domain.user.presentation.dto.request.EmailCheckRequest;
import com.example.be12th.domain.user.presentation.dto.request.EmailRequest;
import com.example.be12th.domain.user.presentation.dto.request.UserRequest;
import com.example.be12th.domain.user.presentation.dto.response.LoginResponse;
import com.example.be12th.domain.user.service.UserCheckMailSignupService;
import com.example.be12th.domain.user.service.UserLoginService;
import com.example.be12th.domain.user.service.UserMailService;
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

    /**
     * Sends a verification email using the information in the request.
     *
     * @param emailRequest the email details, including recipient address and message data
     */
    @PostMapping("/email")
    @ResponseStatus(HttpStatus.OK)
    public void sendEmail(@RequestBody EmailRequest emailRequest) {
        userMailService.execute(emailRequest);
    }

    /**
     * Validates an email verification request for signup.
     *
     * @param emailCheckRequest request containing the email and verification data for signup validation
     * @return {@code true} if the provided verification data is valid for the email, {@code false} otherwise
     */
    @PostMapping("/verify/signup")
    @ResponseStatus(HttpStatus.OK)
    public boolean verifyEmail(@RequestBody EmailCheckRequest emailCheckRequest) {
        return userCheckMailService.execute(emailCheckRequest);
    }

    /**
     * Authenticates a user using the provided credentials and returns authentication details.
     *
     * @param userRequest the user's login credentials (e.g., email or username and password)
     * @return a LoginResponse containing authentication information such as an access token and user data
     */
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody UserRequest userRequest) {
        return userLoginService.execute(userRequest);
    }
}
