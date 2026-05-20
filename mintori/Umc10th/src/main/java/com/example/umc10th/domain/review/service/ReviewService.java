package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.review.dto.request.MyReviewCursorRequest;
import com.example.umc10th.domain.review.dto.request.ReviewRequest;
import com.example.umc10th.domain.review.dto.response.MyReviewCursorResponse;
import com.example.umc10th.domain.review.dto.response.ReviewPageResponse;
import com.example.umc10th.domain.review.dto.response.ReviewResponse;

public interface ReviewService {

    ReviewResponse writeReview(ReviewRequest request);

    ReviewPageResponse getMyReviews(Long userId, int page, int size);

    MyReviewCursorResponse getMyReviewsByCursor(MyReviewCursorRequest request);

    ReviewPageResponse getStoreReviews(Long storeId, int page, int size);
}
