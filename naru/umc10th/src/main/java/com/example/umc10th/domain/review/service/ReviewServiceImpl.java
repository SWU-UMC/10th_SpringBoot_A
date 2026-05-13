package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.mission.entity.enums.MissionStatus;
import com.example.umc10th.domain.mission.entity.mapping.UserMission;
import com.example.umc10th.domain.mission.repository.UserMissionRepository;
import com.example.umc10th.domain.review.converter.ReviewConverter;
import com.example.umc10th.domain.review.dto.ReviewRequestDto;
import com.example.umc10th.domain.review.dto.ReviewResponseDto;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.entity.ReviewAnswer;
import com.example.umc10th.domain.review.entity.ReviewImage;
import com.example.umc10th.domain.review.entity.enums.ReviewSortType;
import com.example.umc10th.domain.review.exception.ReviewException;
import com.example.umc10th.domain.review.exception.code.ReviewErrorCode;
import com.example.umc10th.domain.review.repository.ReviewAnswerRepository;
import com.example.umc10th.domain.review.repository.ReviewImageRepository;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import com.example.umc10th.domain.user.entity.User;
import com.example.umc10th.domain.user.exception.UserException;
import com.example.umc10th.domain.user.exception.code.UserErrorCode;
import com.example.umc10th.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final ReviewAnswerRepository reviewAnswerRepository;
    private final UserRepository userRepository;
    private final UserMissionRepository userMissionRepository;
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int MAX_PAGE_SIZE = 50;

    @Override
    @Transactional
    public ReviewResponseDto.CreateReviewResultDto createReview(
            Long userMissionId,
            ReviewRequestDto.CreateReviewDto request
    ) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        UserMission userMission = userMissionRepository.findById(userMissionId)
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.USER_MISSION_NOT_FOUND));

        validateWritableReview(user, userMission);

        Review review = reviewRepository.save(ReviewConverter.toReview(request, user, userMission));
        List<ReviewImage> reviewImages = ReviewConverter.toReviewImages(review, request.imageKeys());
        reviewImageRepository.saveAll(reviewImages);

        return ReviewConverter.toCreateReviewResultDto(review, request.imageKeys());
    }

    @Override
    public ReviewResponseDto.MyReviewCursorResultDto getMyReviews(
            Long userId,
            ReviewSortType sort,
            Long cursorId,
            Double cursorRating,
            int size
    ) {
        validateUser(userId);

        ReviewSortType sortType = sort == null ? ReviewSortType.ID : sort;
        int pageSize = normalizeSize(size);
        Slice<Review> reviews = switch (sortType) {
            case ID -> reviewRepository.findMyReviewsOrderById(userId, cursorId, PageRequest.of(0, pageSize));
            case RATING -> {
                validateRatingCursor(cursorId, cursorRating);
                yield reviewRepository.findMyReviewsOrderByRating(userId, cursorRating, cursorId, PageRequest.of(0, pageSize));
            }
        };

        List<Long> reviewIds = reviews.getContent().stream()
                .map(Review::getId)
                .toList();

        Map<Long, List<String>> imageKeysByReviewId = getImageKeysByReviewId(reviewIds);
        Map<Long, ReviewAnswer> answerByReviewId = getLatestAnswerByReviewId(reviewIds);

        return ReviewConverter.toMyReviewCursorResultDto(reviews, imageKeysByReviewId, answerByReviewId);
    }

    private void validateWritableReview(User user, UserMission userMission) {
        if (!userMission.getUser().getId().equals(user.getId())) {
            throw new ReviewException(ReviewErrorCode.USER_MISSION_OWNER_MISMATCH);
        }

        if (userMission.getStatus() != MissionStatus.SUCCESS) {
            throw new ReviewException(ReviewErrorCode.USER_MISSION_NOT_SUCCESS);
        }

        if (reviewRepository.existsByUserMissionId(userMission.getId())) {
            throw new ReviewException(ReviewErrorCode.REVIEW_ALREADY_EXISTS);
        }
    }

    private void validateUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserException(UserErrorCode.USER_NOT_FOUND);
        }
    }

    private int normalizeSize(int size) {
        if (size <= 0) {
            return DEFAULT_PAGE_SIZE;
        }

        return Math.min(size, MAX_PAGE_SIZE);
    }

    private void validateRatingCursor(Long cursorId, Double cursorRating) {
        if (cursorId != null && cursorRating == null) {
            throw new ReviewException(ReviewErrorCode.INVALID_REVIEW_CURSOR);
        }
    }

    private Map<Long, List<String>> getImageKeysByReviewId(List<Long> reviewIds) {
        if (reviewIds.isEmpty()) {
            return Collections.emptyMap();
        }

        return reviewImageRepository.findByReview_IdInOrderByReview_IdAscSequenceAsc(reviewIds).stream()
                .collect(Collectors.groupingBy(
                        reviewImage -> reviewImage.getReview().getId(),
                        Collectors.mapping(ReviewImage::getImageKey, Collectors.toList())
                ));
    }

    private Map<Long, ReviewAnswer> getLatestAnswerByReviewId(List<Long> reviewIds) {
        if (reviewIds.isEmpty()) {
            return Collections.emptyMap();
        }

        return reviewAnswerRepository.findByReview_IdInOrderByReview_IdAscCreatedAtDescIdDesc(reviewIds).stream()
                .collect(Collectors.toMap(
                        reviewAnswer -> reviewAnswer.getReview().getId(),
                        reviewAnswer -> reviewAnswer,
                        (latest, ignored) -> latest
                ));
    }
}
