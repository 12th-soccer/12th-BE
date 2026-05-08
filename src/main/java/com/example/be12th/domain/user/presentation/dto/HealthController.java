package com.example.be12th.domain.user.presentation.dto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/")
    public String home() {
        return "server running";
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
