package com.example.umc10th.domain.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ReviewReqDto {

    public record Create(
            @NotNull Long memberId,
            @NotNull Long storeId,
            @NotBlank String content,
            @Min(0) @Max(5) float score
    ) {
    }

    public record MyReviewsByIdCursorRequest(
            @NotNull @Positive Long memberId,
            @Positive Long cursorId,
            @NotNull @Min(1) @Max(50) Integer size
    ) {
    }

    public record MyReviewsByScoreCursorRequest(
            @NotNull @Positive Long memberId,
            @Min(0) @Max(5) Float cursorScore,
            @Positive Long cursorId,
            @NotNull @Min(1) @Max(50) Integer size
    ) {
    }
}
