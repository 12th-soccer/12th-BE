package com.example.be12th.domain.join.presentation;

import com.example.be12th.domain.join.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/join")
public class JoinController {
    private final JoinService joinService;

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void join(@PathVariable Long id) {
        joinService.execute(id);
    }
}
