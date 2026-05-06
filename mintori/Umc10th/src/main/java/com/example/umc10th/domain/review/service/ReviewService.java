package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.review.dto.request.ReviewRequest;
import com.example.umc10th.domain.review.dto.response.ReviewPageResponse;
import com.example.umc10th.domain.review.dto.response.ReviewResponse;

public interface ReviewService {

    /** 가게에 리뷰 작성 */
    ReviewResponse writeReview(ReviewRequest request);

    /** 내가 작성한 리뷰 목록 페이징 조회 */
    ReviewPageResponse getMyReviews(Long userId, int page, int size);
}
