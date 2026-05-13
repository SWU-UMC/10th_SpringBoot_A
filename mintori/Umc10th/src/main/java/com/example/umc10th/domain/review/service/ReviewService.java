package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.review.dto.request.MyReviewCursorRequest;
import com.example.umc10th.domain.review.dto.request.ReviewRequest;
import com.example.umc10th.domain.review.dto.response.MyReviewCursorResponse;
import com.example.umc10th.domain.review.dto.response.ReviewPageResponse;
import com.example.umc10th.domain.review.dto.response.ReviewResponse;

public interface ReviewService {

    /** 가게에 리뷰 작성 */
    ReviewResponse writeReview(ReviewRequest request);

    /** 내가 작성한 리뷰 목록 페이징 조회 (오프셋 기반 - 기존 유지) */
    ReviewPageResponse getMyReviews(Long userId, int page, int size);

    /** [필수 2] 내가 작성한 리뷰 목록 - 커서 기반 페이지네이션 (ID 순 / 별점 순) */
    MyReviewCursorResponse getMyReviewsByCursor(MyReviewCursorRequest request);

    /** 특정 가게의 리뷰 목록 페이징 조회 */
    ReviewPageResponse getStoreReviews(Long storeId, int page, int size);
}
