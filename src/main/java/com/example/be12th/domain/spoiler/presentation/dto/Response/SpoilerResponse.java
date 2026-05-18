package com.example.be12th.domain.spoiler.presentation.dto.Response;

import com.example.be12th.domain.spoiler.domain.SpoilerSetting;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SpoilerResponse {
    private Boolean spoilerEnabled;

    public static SpoilerResponse from(SpoilerSetting spoilerSetting) {
        return new SpoilerResponse(
                spoilerSetting.getSpoilerEnabled()
        );
    }
}
