package com.example.umc10th.domain.review.converter;

import com.example.umc10th.domain.mission.entity.mapping.UserMission;
import com.example.umc10th.domain.review.dto.ReviewRequestDto;
import com.example.umc10th.domain.review.dto.ReviewResponseDto;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.entity.ReviewAnswer;
import com.example.umc10th.domain.review.entity.ReviewImage;
import com.example.umc10th.domain.user.entity.User;
import org.springframework.data.domain.Slice;

import java.util.Collections;
import java.util.List;
import java.util.Map;
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

    public static ReviewResponseDto.MyReviewCursorResultDto toMyReviewCursorResultDto(
            Slice<Review> reviews,
            Map<Long, List<String>> imageKeysByReviewId,
            Map<Long, ReviewAnswer> answerByReviewId
    ) {
        List<ReviewResponseDto.MyReviewDto> content = reviews.getContent().stream()
                .map(review -> toMyReviewDto(
                        review,
                        imageKeysByReviewId.getOrDefault(review.getId(), Collections.emptyList()),
                        answerByReviewId.get(review.getId())
                ))
                .toList();

        Review lastReview = reviews.getContent().isEmpty() ? null : reviews.getContent().get(reviews.getContent().size() - 1);

        return ReviewResponseDto.MyReviewCursorResultDto.builder()
                .content(content)
                .hasNext(reviews.hasNext())
                .nextCursorId(lastReview == null ? null : lastReview.getId())
                .nextCursorRating(lastReview == null ? null : lastReview.getRating())
                .build();
    }

    private static ReviewResponseDto.MyReviewDto toMyReviewDto(
            Review review,
            List<String> imageKeys,
            ReviewAnswer answer
    ) {
        return ReviewResponseDto.MyReviewDto.builder()
                .reviewId(review.getId())
                .nickname(review.getUser().getName())
                .createdAt(review.getCreatedAt())
                .rating(review.getRating())
                .content(review.getContent())
                .imageKeys(imageKeys)
                .ownerReply(toOwnerReplyDto(answer))
                .build();
    }

    private static ReviewResponseDto.OwnerReplyDto toOwnerReplyDto(ReviewAnswer answer) {
        if (answer == null) {
            return null;
        }

        return ReviewResponseDto.OwnerReplyDto.builder()
                .content(answer.getContent())
                .createdAt(answer.getCreatedAt())
                .build();
    }
}
