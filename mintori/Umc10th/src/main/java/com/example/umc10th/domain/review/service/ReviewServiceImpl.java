package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.review.converter.ReviewConverter;
import com.example.umc10th.domain.review.dto.request.ReviewRequest;
import com.example.umc10th.domain.review.dto.response.ReviewPageResponse;
import com.example.umc10th.domain.review.dto.response.ReviewResponse;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import com.example.umc10th.domain.store.entity.Store;
import com.example.umc10th.domain.store.exception.StoreException;
import com.example.umc10th.domain.store.exception.code.StoreErrorCode;
import com.example.umc10th.domain.store.repository.StoreRepository;
import com.example.umc10th.domain.user.entity.User;
import com.example.umc10th.domain.user.exception.UserException;
import com.example.umc10th.domain.user.exception.code.UserErrorCode;
import com.example.umc10th.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    @Override
    @Transactional
    public ReviewResponse writeReview(ReviewRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        Store store = storeRepository.findById(request.storeId())
                .orElseThrow(() -> new StoreException(StoreErrorCode.STORE_NOT_FOUND));

        Review review = ReviewConverter.toReview(request, user, store);
        Review saved = reviewRepository.save(review);
        return ReviewConverter.toReviewResponse(saved);
    }

    @Override
    public ReviewPageResponse getMyReviews(Long userId, int page, int size) {
        if (!userRepository.existsById(userId)) {
            throw new UserException(UserErrorCode.USER_NOT_FOUND);
        }

        Page<Review> result = reviewRepository.findByUserId(
                userId,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        );
        return ReviewConverter.toReviewPageResponse(result);
    }

    @Override
    public ReviewPageResponse getStoreReviews(Long storeId, int page, int size) {
        if (!storeRepository.existsById(storeId)) {
            throw new StoreException(StoreErrorCode.STORE_NOT_FOUND);
        }

        Page<Review> result = reviewRepository.findByStoreId(
                storeId,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        );
        return ReviewConverter.toReviewPageResponse(result);
    }
}
