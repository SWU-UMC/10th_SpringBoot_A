package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.review.dto.ReviewRequestDto;
import com.example.umc10th.domain.review.dto.ReviewResponseDto;
import com.example.umc10th.domain.review.entity.enums.ReviewSortType;

public interface ReviewService {

    ReviewResponseDto.CreateReviewResultDto createReview(Long userMissionId, ReviewRequestDto.CreateReviewDto request);

    ReviewResponseDto.MyReviewCursorResultDto getMyReviews(
            Long userId,
            ReviewSortType sort,
            Long cursorId,
            Double cursorRating,
            int size
    );
}
