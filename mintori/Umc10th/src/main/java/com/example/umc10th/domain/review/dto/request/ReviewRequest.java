package com.example.umc10th.domain.review.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ReviewRequest(
        @NotNull(message = "유저 ID를 입력해주세요")
        Long userId,

        @NotNull(message = "가게 ID를 입력해주세요")
        Long storeId,

        @NotBlank(message = "리뷰 내용을 입력해주세요")
        @Size(max = 1000)
        String body,

        @NotNull(message = "별점을 입력해주세요")
        @DecimalMin(value = "0.0", message = "별점은 0.0 이상이어야 합니다")
        @DecimalMax(value = "5.0", message = "별점은 5.0 이하여야 합니다")
        BigDecimal score
) {
}
