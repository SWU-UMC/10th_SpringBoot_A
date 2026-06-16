package com.example.umc10th.domain.review.converter;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.store.entity.Store;
import com.example.umc10th.domain.review.dto.ReviewReqDto;
import com.example.umc10th.domain.review.dto.ReviewResDto;
import com.example.umc10th.domain.review.entity.Review;
import java.util.List;
import org.springframework.data.domain.Slice;

public final class ReviewConverter {

    private ReviewConverter() {
    }

    public static Review toEntity(ReviewReqDto.Create request, Member member, Store store) {
        return Review.builder()
                .member(member)
                .store(store)
                .content(request.content())
                .score(request.score())
                .build();
    }

    public static ReviewResDto.ReviewInfo toReviewInfo(Review review) {
        return new ReviewResDto.ReviewInfo(
                review.getId(),
                review.getMember().getId(),
                review.getStore().getId(),
                review.getContent(),
                review.getScore()
        );
    }

    public static ReviewResDto.MyReviewItem toMyReviewItem(Review review) {
        return new ReviewResDto.MyReviewItem(
                review.getId(),
                review.getStore().getId(),
                review.getStore().getName(),
                review.getContent(),
                review.getScore()
        );
    }

    public static ReviewResDto.MyReviewsByIdCursorResponse toMyReviewsByIdCursorResponse(
            Slice<Review> slice
    ) {
        List<Review> reviews = slice.getContent();
        List<ReviewResDto.MyReviewItem> items = reviews.stream()
                .map(ReviewConverter::toMyReviewItem)
                .toList();

        Long nextCursorId = slice.hasNext()
                ? reviews.get(reviews.size() - 1).getId()
                : null;

        return new ReviewResDto.MyReviewsByIdCursorResponse(items, nextCursorId, slice.hasNext());
    }

    public static ReviewResDto.MyReviewsByScoreCursorResponse toMyReviewsByScoreCursorResponse(
            Slice<Review> slice
    ) {
        List<Review> reviews = slice.getContent();
        List<ReviewResDto.MyReviewItem> items = reviews.stream()
                .map(ReviewConverter::toMyReviewItem)
                .toList();

        Review lastReview = slice.hasNext()
                ? reviews.get(reviews.size() - 1)
                : null;
        Float nextCursorScore = lastReview == null ? null : lastReview.getScore();
        Long nextCursorId = lastReview == null ? null : lastReview.getId();

        return new ReviewResDto.MyReviewsByScoreCursorResponse(
                items,
                nextCursorScore,
                nextCursorId,
                slice.hasNext()
        );
    }
}
