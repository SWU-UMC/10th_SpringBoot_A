package com.example.umc10th.domain.review.converter;

import com.example.umc10th.domain.review.dto.request.ReviewRequest;
import com.example.umc10th.domain.review.dto.response.MyReviewCursorResponse;
import com.example.umc10th.domain.review.dto.response.ReviewPageResponse;
import com.example.umc10th.domain.review.dto.response.ReviewResponse;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.enums.ReviewSortType;
import com.example.umc10th.domain.store.entity.Store;
import com.example.umc10th.domain.user.entity.User;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
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

    /**
     * 커서 기반 페이지네이션 응답 변환.
     *  - rows 는 size+1 개를 조회한 결과 (다음 페이지 존재 여부 판단용)
     *  - 마지막 아이템의 (id, score) 를 다음 cursor 로 노출
     *  - sortType == ID 인 경우 nextCursorScore 는 항상 null
     */
    public static MyReviewCursorResponse toMyReviewCursorResponse(
            List<Review> rows,
            int size,
            ReviewSortType sortType
    ) {
        boolean hasNext = rows.size() > size;
        List<Review> page = hasNext ? rows.subList(0, size) : rows;

        Long nextCursorId = null;
        BigDecimal nextCursorScore = null;

        if (hasNext && !page.isEmpty()) {
            Review last = page.get(page.size() - 1);
            nextCursorId = last.getId();
            if (sortType == ReviewSortType.SCORE) {
                nextCursorScore = last.getScore();
            }
        }

        List<ReviewResponse> reviews = page.stream()
                .map(ReviewConverter::toReviewResponse)
                .toList();

        return new MyReviewCursorResponse(
                reviews,
                size,
                hasNext,
                nextCursorId,
                nextCursorScore
        );
    }
}
