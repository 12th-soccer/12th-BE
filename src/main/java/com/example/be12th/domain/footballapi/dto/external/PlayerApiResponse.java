package com.example.be12th.domain.footballapi.dto.external;

import java.util.List;

public record PlayerApiResponse(
        List<PlayerItem> response
) {
}
