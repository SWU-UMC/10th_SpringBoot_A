package com.example.umc10th.domain.review.dto;

import java.util.List;

public class ReviewResDto {

    public record ReviewInfo(
            Long reviewId,
            Long memberId,
            Long storeId,
            String content,
            float score
    ) {
    }

    public record MyReviewItem(
            Long reviewId,
            Long storeId,
            String storeName,
            String content,
            Float score
    ) {
    }

    public record MyReviewsByIdCursorResponse(
            List<MyReviewItem> reviews,
            Long nextCursorId,
            Boolean hasNext
    ) {
    }

    public record MyReviewsByScoreCursorResponse(
            List<MyReviewItem> reviews,
            Float nextCursorScore,
            Long nextCursorId,
            Boolean hasNext
    ) {
    }
}
