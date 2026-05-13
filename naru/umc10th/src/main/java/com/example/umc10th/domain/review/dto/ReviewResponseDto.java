package com.example.umc10th.domain.review.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewResponseDto {

    @Builder
    public record CreateReviewResultDto(
            Long reviewId,
            Long userId,
            Long userMissionId,
            Double rating,
            String content,
            List<String> imageKeys,
            LocalDateTime createdAt
    ) {
    }

    @Builder
    public record MyReviewCursorResultDto(
            List<MyReviewDto> content,
            Boolean hasNext,
            Long nextCursorId,
            Double nextCursorRating
    ) {
    }

    @Builder
    public record MyReviewDto(
            Long reviewId,
            String nickname,
            LocalDateTime createdAt,
            Double rating,
            String content,
            List<String> imageKeys,
            OwnerReplyDto ownerReply
    ) {
    }

    @Builder
    public record OwnerReplyDto(
            String content,
            LocalDateTime createdAt
    ) {
    }
}
