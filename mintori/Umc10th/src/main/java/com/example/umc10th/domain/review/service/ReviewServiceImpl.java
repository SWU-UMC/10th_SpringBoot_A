package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.review.converter.ReviewConverter;
import com.example.umc10th.domain.review.dto.request.MyReviewCursorRequest;
import com.example.umc10th.domain.review.dto.request.ReviewRequest;
import com.example.umc10th.domain.review.dto.response.MyReviewCursorResponse;
import com.example.umc10th.domain.review.dto.response.ReviewPageResponse;
import com.example.umc10th.domain.review.dto.response.ReviewResponse;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.enums.ReviewSortType;
import com.example.umc10th.domain.review.exception.ReviewException;
import com.example.umc10th.domain.review.exception.code.ReviewErrorCode;
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

import java.math.BigDecimal;
import java.util.List;

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

    /**
     * [필수 2] 내가 작성한 리뷰 목록 - 커서 기반 페이지네이션
     * - 사진 제외 (Review 엔티티에 사진 필드 없음)
     * - ID 순 / 별점 순 모두 지원
     * - size + 1 개를 조회하여 hasNext 판정
     */
    @Override
    public MyReviewCursorResponse getMyReviewsByCursor(MyReviewCursorRequest request) {
        Long userId = request.userId();
        ReviewSortType sortType = request.sortType();
        int size = request.size();

        if (!userRepository.existsById(userId)) {
            throw new UserException(UserErrorCode.USER_NOT_FOUND);
        }
        if (sortType == null) {
            throw new ReviewException(ReviewErrorCode.INVALID_REVIEW_SORT_TYPE);
        }

        // size + 1 개를 조회해 hasNext 판단
        PageRequest limit = PageRequest.of(0, size + 1);

        List<Review> rows;
        if (sortType == ReviewSortType.ID) {
            rows = reviewRepository.findMyReviewsByCursorId(
                    userId,
                    request.cursorId(),
                    limit
            );
        } else {
            // SCORE 정렬: cursorScore + cursorId 동시 필요 (둘 다 null 이면 첫 페이지)
            BigDecimal cursorScore = request.cursorScore();
            Long cursorId = request.cursorId();
            if ((cursorScore == null) ^ (cursorId == null)) {
                // 둘 중 하나만 들어오면 페이지가 끊김
                throw new ReviewException(ReviewErrorCode.INVALID_REVIEW_CURSOR);
            }
            rows = reviewRepository.findMyReviewsByCursorScore(
                    userId,
                    cursorScore,
                    cursorId,
                    limit
            );
        }

        return ReviewConverter.toMyReviewCursorResponse(rows, size, sortType);
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
