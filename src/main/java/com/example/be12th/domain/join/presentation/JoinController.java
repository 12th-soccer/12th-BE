package com.example.be12th.domain.join.presentation;

import com.example.be12th.domain.join.presentation.dto.response.JoinResponse;
import com.example.be12th.domain.join.service.JoinQueryAllService;
import com.example.be12th.domain.join.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/join")
public class JoinController {
    private final JoinService joinService;
    private final JoinQueryAllService joinQueryAllService;

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void join(@PathVariable Long id) {
        joinService.execute(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<JoinResponse> getAllJoins() {
        return joinQueryAllService.execute();
    }
}
