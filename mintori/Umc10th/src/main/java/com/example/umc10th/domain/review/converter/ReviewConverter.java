package com.example.umc10th.domain.review.converter;

import com.example.umc10th.domain.review.dto.request.ReviewRequest;
import com.example.umc10th.domain.review.dto.response.ReviewPageResponse;
import com.example.umc10th.domain.review.dto.response.ReviewResponse;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.store.entity.Store;
import com.example.umc10th.domain.user.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public class ReviewConverter {

    private ReviewConverter() {
    }

    public static Review toReview(ReviewRequest request, User user, Store store) {
        return Review.builder()
                .user(user)
                .store(store)
                .body(request.body())
                .score(request.score())
                .build();
    }

    public static ReviewResponse toReviewResponse(Review review) {
        Store store = review.getStore();
        return new ReviewResponse(
                review.getId(),
                review.getUser().getId(),
                store.getId(),
                store.getName(),
                review.getBody(),
                review.getScore(),
                review.getCreatedAt()
        );
    }

    public static ReviewPageResponse toReviewPageResponse(Page<Review> page) {
        List<ReviewResponse> reviews = page.getContent().stream()
                .map(ReviewConverter::toReviewResponse)
                .toList();
        return new ReviewPageResponse(
                reviews,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.hasNext()
        );
    }
}
