package com.example.be12th.domain.recruitment.presentation.dto.request;

import com.example.be12th.domain.recruitment.domain.category.AgeGroup;
import com.example.be12th.domain.recruitment.domain.category.GenderGroup;
import com.example.be12th.domain.recruitment.domain.category.K1Group;
import com.example.be12th.domain.recruitment.domain.category.K2Group;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RecruitmentRequest(
        @NotBlank
        @Size(max = 50)
        String title,

        @NotBlank
        @Size(max = 255)
        String content,

        @NotNull
        @Min(value = 4, message = "모집 인원은 4명 이상이어야 합니다.")
        @Max(value = 10, message = "모집 인원은 10명 이하여야 합니다.")
        Integer headCount,

        @NotNull
        AgeGroup ageGroup,
        @NotNull
        GenderGroup genderGroup,
        K1Group k1Group,
        K2Group k2Group
) {
    @AssertTrue(message = "k1Group 또는 k2Group 중 하나만 선택해야 합니다.")
    public boolean hasExactlyOneLeagueGroup() {
        return (k1Group == null) != (k2Group == null);
    }
}
