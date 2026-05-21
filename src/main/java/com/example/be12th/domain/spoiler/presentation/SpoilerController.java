package com.example.be12th.domain.spoiler.presentation;

import com.example.be12th.domain.spoiler.presentation.dto.Request.SpoilerRequest;
import com.example.be12th.domain.spoiler.presentation.dto.Response.SpoilerResponse;
import com.example.be12th.domain.spoiler.service.SpoilerSettingService;
import com.example.be12th.domain.spoiler.service.SpoilerUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/spoiler")
public class SpoilerController {
    private final SpoilerSettingService spoilerSettingService;
    private final SpoilerUpdateService spoilerUpdateService;

    @GetMapping
    public SpoilerResponse getSetting(){
        return spoilerSettingService.execute();
    }

    @PatchMapping
    public SpoilerResponse updateSetting(@RequestBody SpoilerRequest spoilerRequest){
        return spoilerUpdateService.execute(spoilerRequest);
    }
}
