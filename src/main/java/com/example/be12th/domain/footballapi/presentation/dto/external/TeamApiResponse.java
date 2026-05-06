package com.example.be12th.domain.footballapi.presentation.dto.external;

import java.util.List;

public record TeamApiResponse(
        List<TeamDetailItem> response
) {
}
