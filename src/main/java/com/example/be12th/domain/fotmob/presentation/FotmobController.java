package com.example.be12th.domain.fotmob.presentation;

import com.example.be12th.domain.fotmob.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fotmob")
public class FotmobController {
    private final ClubService clubService;

    @PostMapping("/save")
    public String save() throws Exception {
        clubService.execute();
        return "ok";
    }
}
