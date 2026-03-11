package com.example.be12th.domain.user.presentation;

import com.example.be12th.domain.user.presentation.dto.request.EmailCheckRequest;
import com.example.be12th.domain.user.presentation.dto.request.EmailRequest;
import com.example.be12th.domain.user.service.UserCheckMailService;
import com.example.be12th.domain.user.service.UserMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserCheckMailService userCheckMailService;
    private final UserMailService userMailService;

    @PostMapping("/email")
    @ResponseStatus(HttpStatus.OK)
    public void sendEmail(@RequestBody EmailRequest emailRequest) {
        userMailService.execute(emailRequest);
    }

    @PostMapping("/email/verify")
    @ResponseStatus(HttpStatus.OK)
    public boolean verifyEmail(@RequestBody EmailCheckRequest emailCheckRequest) {
        return userCheckMailService.execute(emailCheckRequest);
    }
}
