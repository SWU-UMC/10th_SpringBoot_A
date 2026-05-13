package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.review.dto.ReviewRequestDto;
import com.example.umc10th.domain.review.dto.ReviewResponseDto;

public interface ReviewService {

    ReviewResponseDto.CreateReviewResultDto createReview(Long userMissionId, ReviewRequestDto.CreateReviewDto request);
}
