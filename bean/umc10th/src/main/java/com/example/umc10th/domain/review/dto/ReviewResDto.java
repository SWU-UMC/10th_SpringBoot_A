package com.example.umc10th.domain.review.dto;

public class ReviewResDto {

    public record ReviewInfo(
            Long reviewId,
            Long memberId,
            Long storeId,
            String content,
            float score
    ) {
    }
}