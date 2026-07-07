package com.example.be12th.domain.footballapi.dto.external;

import java.util.List;

public record PlayerApiResponse(
        PagingInfo paging,
        List<PlayerItem> response
) {
}
