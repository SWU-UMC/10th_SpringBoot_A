package com.example.umc10th.domain.review.converter;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.store.entity.Store;
import com.example.umc10th.domain.review.dto.ReviewReqDto;
import com.example.umc10th.domain.review.dto.ReviewResDto;
import com.example.umc10th.domain.review.entity.Review;

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
}