package com.example.umc10th.domain.review.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReviewResponse(
        Long reviewId,
        Long userId,
        Long storeId,
        String storeName,
        String body,
        BigDecimal score,
        LocalDateTime createdAt
) {
}
