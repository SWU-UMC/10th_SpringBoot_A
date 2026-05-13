package com.example.umc10th.domain.review.converter;

import com.example.umc10th.domain.mission.entity.mapping.UserMission;
import com.example.umc10th.domain.review.dto.ReviewRequestDto;
import com.example.umc10th.domain.review.dto.ReviewResponseDto;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.entity.ReviewImage;
import com.example.umc10th.domain.user.entity.User;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class ReviewConverter {

    public static Review toReview(
            ReviewRequestDto.CreateReviewDto request,
            User user,
            UserMission userMission
    ) {
        return Review.builder()
                .rating(request.rating())
                .content(request.content())
                .user(user)
                .userMission(userMission)
                .build();
    }

    public static List<ReviewImage> toReviewImages(
            Review review,
            List<String> imageKeys
    ) {
        if (imageKeys == null || imageKeys.isEmpty()) {
            return Collections.emptyList();
        }

        return IntStream.range(0, imageKeys.size())
                .mapToObj(index -> ReviewImage.builder()
                        .review(review)
                        .imageKey(imageKeys.get(index))
                        .sequence(index + 1)
                        .build())
                .toList();
    }

    public static ReviewResponseDto.CreateReviewResultDto toCreateReviewResultDto(
            Review review,
            List<String> imageKeys
    ) {
        return ReviewResponseDto.CreateReviewResultDto.builder()
                .reviewId(review.getId())
                .userId(review.getUser().getId())
                .userMissionId(review.getUserMission().getId())
                .rating(review.getRating())
                .content(review.getContent())
                .imageKeys(imageKeys == null ? Collections.emptyList() : imageKeys)
                .createdAt(review.getCreatedAt())
                .build();
    }
}
