package com.example.umc10th.domain.review.dto.request;

import com.example.umc10th.domain.review.enums.ReviewSortType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;


public record MyReviewCursorRequest(
        @NotNull(message = "유저 ID를 입력해주세요")
        @Positive(message = "유저 ID는 양수여야 합니다")
        Long userId,

        @NotNull(message = "정렬 기준을 입력해주세요 (ID / SCORE)")
        ReviewSortType sortType,

        Long cursorId,

        BigDecimal cursorScore,

        @NotNull(message = "size 값을 입력해주세요")
        @Min(value = 1, message = "size 는 1 이상이어야 합니다")
        Integer size
) {
}
