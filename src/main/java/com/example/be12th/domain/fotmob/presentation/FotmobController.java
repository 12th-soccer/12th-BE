package com.example.be12th.domain.fotmob.presentation;

import com.example.be12th.domain.fotmob.service.ClubService;
import com.example.be12th.domain.fotmob.service.PlayerImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fotmob")
public class FotmobController {
    private final ClubService clubService;
    private final PlayerImportService playerImportService;

    @PostMapping("/save")
    public String save() throws Exception {
        clubService.execute();
        return "ok";
    }

    @PostMapping("/player/save")
    public String savePlayer()throws Exception {
        playerImportService.execute();
        return "ok";
    }
}
