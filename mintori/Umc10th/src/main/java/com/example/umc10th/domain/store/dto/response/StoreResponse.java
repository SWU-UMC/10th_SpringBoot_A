package com.example.umc10th.domain.store.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record StoreResponse(
        Long storeId,
        Long regionId,
        String name,
        String address,
        BigDecimal score,
        LocalDateTime createdAt
) {
}
