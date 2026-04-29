package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.review.dto.ReviewReqDto;
import com.example.umc10th.domain.review.dto.ReviewResDto;

public interface ReviewService {
    ReviewResDto.ReviewInfo getReview(Long reviewId);

    ReviewResDto.ReviewInfo createReview(ReviewReqDto.Create request);
}