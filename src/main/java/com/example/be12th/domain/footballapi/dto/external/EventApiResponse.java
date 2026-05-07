package com.example.be12th.domain.footballapi.dto.external;

import java.util.List;

public record EventApiResponse(
        List<EventItem> response
) {
}
