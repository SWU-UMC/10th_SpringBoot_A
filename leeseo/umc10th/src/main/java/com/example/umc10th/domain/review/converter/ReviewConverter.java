package com.example.umc10th.domain.review.converter;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.review.dto.ReviewInfo;
import com.example.umc10th.domain.review.dto.ReviewPhotoUrl;
import com.example.umc10th.domain.review.dto.ReviewReqDto;
import com.example.umc10th.domain.review.dto.ReviewResDto;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.entity.ReviewPhoto;

import java.util.List;

public class ReviewConverter {

    public static Review toReview(
            Member member,
            Store store,
            ReviewReqDto.Review dto
    ) {
        return Review.builder()
                .member(member)
                .store(store)
                .content(dto.content())
                .rate(dto.rate())
                .build();
    }

    public static ReviewInfo toReviewInfo(
            Review review
    ) {
        return ReviewInfo.builder()
                .rate(review.getRate())
                .content(review.getContent())
                .reviewerNickname(review.getMember().getNickname())
                .reviewPhotoList(review.getReviewPhotoList().stream().map(ReviewConverter::toReviewPhotoUrl).toList())
                .createdAt(review.getCreatedAt())
                .build();
    }

    public static ReviewPhotoUrl toReviewPhotoUrl(
            ReviewPhoto reviewPhoto
    ) {
        return ReviewPhotoUrl.builder()
                .reviewPhoto(reviewPhoto.getImageUrl())
                .build();
    }

    public static <T> ReviewResDto.Pagination<T> toPagination(
            List<T> data,
            boolean hasNext,
            String nextCursor,
            Integer pageSize
    ) {
        return ReviewResDto.Pagination.<T>builder()
                .data(data)
                .hasNext(hasNext)
                .pageSize(pageSize)
                .nextCursor(nextCursor)
                .build();
    }
}
