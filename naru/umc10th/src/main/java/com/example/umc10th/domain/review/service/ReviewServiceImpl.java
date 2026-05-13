package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.mission.entity.enums.MissionStatus;
import com.example.umc10th.domain.mission.entity.mapping.UserMission;
import com.example.umc10th.domain.mission.repository.UserMissionRepository;
import com.example.umc10th.domain.review.converter.ReviewConverter;
import com.example.umc10th.domain.review.dto.ReviewRequestDto;
import com.example.umc10th.domain.review.dto.ReviewResponseDto;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.entity.ReviewImage;
import com.example.umc10th.domain.review.exception.ReviewException;
import com.example.umc10th.domain.review.exception.code.ReviewErrorCode;
import com.example.umc10th.domain.review.repository.ReviewImageRepository;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import com.example.umc10th.domain.user.entity.User;
import com.example.umc10th.domain.user.exception.UserException;
import com.example.umc10th.domain.user.exception.code.UserErrorCode;
import com.example.umc10th.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final UserRepository userRepository;
    private final UserMissionRepository userMissionRepository;

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
}
