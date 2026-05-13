package com.example.umc10th.domain.review.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class ReviewRequestDto {

    public record CreateReviewDto(
            @NotNull(message = "사용자 ID는 필수입니다.")
            Long userId,

            @NotNull(message = "평점은 필수입니다.")
            @DecimalMin(value = "0.0", message = "평점은 0점 이상이어야 합니다.")
            @DecimalMax(value = "5.0", message = "평점은 5점 이하여야 합니다.")
            Double rating,

            @Size(max = 1000, message = "리뷰 내용은 1000자 이하여야 합니다.")
            String content,

            @Size(max = 5, message = "리뷰 이미지는 최대 5개까지 등록할 수 있습니다.")
            List<String> imageKeys
    ) {
    }
}
