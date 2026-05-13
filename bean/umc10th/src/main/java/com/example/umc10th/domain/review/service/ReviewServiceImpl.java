package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.store.entity.Store;
import com.example.umc10th.domain.store.repository.StoreRepository;
import com.example.umc10th.domain.review.converter.ReviewConverter;
import com.example.umc10th.domain.review.dto.ReviewReqDto;
import com.example.umc10th.domain.review.dto.ReviewResDto;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.exception.ReviewException;
import com.example.umc10th.domain.review.exception.code.ReviewErrorCode;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    @Override
    public ReviewResDto.ReviewInfo getReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND));

        return ReviewConverter.toReviewInfo(review);
    }

    @Override
    @Transactional
    public ReviewResDto.ReviewInfo createReview(ReviewReqDto.Create request) {
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.MEMBER_NOT_FOUND));

        Store store = storeRepository.findById(request.storeId())
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.STORE_NOT_FOUND));

        Review savedReview = reviewRepository.save(ReviewConverter.toEntity(request, member, store));
        return ReviewConverter.toReviewInfo(savedReview);
    }

    @Override
    public ReviewResDto.MyReviewsByIdCursorResponse getMyReviewsByIdCursor(ReviewReqDto.MyReviewsByIdCursorRequest request) {
        memberRepository.findById(request.memberId())
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.MEMBER_NOT_FOUND));

        int fetchSize = request.size() + 1;
        PageRequest pageRequest = PageRequest.of(0, fetchSize);

        List<Review> reviews = request.cursorId() == null
                ? reviewRepository.findMyReviewsByIdCursorFirst(request.memberId(), pageRequest)
                : reviewRepository.findMyReviewsByIdCursorNext(request.memberId(), request.cursorId(), pageRequest);

        boolean hasNext = reviews.size() > request.size();
        List<Review> content = hasNext ? reviews.subList(0, request.size()) : reviews;

        List<ReviewResDto.MyReviewItem> items = content.stream()
                .map(ReviewConverter::toMyReviewItem)
                .toList();

        Long nextCursorId = hasNext ? content.get(content.size() - 1).getId() : null;
        return ReviewConverter.toMyReviewsByIdCursorResponse(items, nextCursorId, hasNext);
    }

    @Override
    public ReviewResDto.MyReviewsByScoreCursorResponse getMyReviewsByScoreCursor(ReviewReqDto.MyReviewsByScoreCursorRequest request) {
        memberRepository.findById(request.memberId())
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.MEMBER_NOT_FOUND));

        boolean bothCursorProvided = request.cursorScore() != null && request.cursorId() != null;
        boolean bothCursorEmpty = request.cursorScore() == null && request.cursorId() == null;
        if (!bothCursorProvided && !bothCursorEmpty) {
            throw new ReviewException(ReviewErrorCode.INVALID_CURSOR);
        }

        int fetchSize = request.size() + 1;
        PageRequest pageRequest = PageRequest.of(0, fetchSize);

        List<Review> reviews = bothCursorEmpty
                ? reviewRepository.findMyReviewsByScoreCursorFirst(request.memberId(), pageRequest)
                : reviewRepository.findMyReviewsByScoreCursorNext(
                request.memberId(),
                request.cursorScore(),
                request.cursorId(),
                pageRequest
        );

        boolean hasNext = reviews.size() > request.size();
        List<Review> content = hasNext ? reviews.subList(0, request.size()) : reviews;

        List<ReviewResDto.MyReviewItem> items = content.stream()
                .map(ReviewConverter::toMyReviewItem)
                .toList();

        Float nextCursorScore = null;
        Long nextCursorId = null;

        if (hasNext) {
            Review lastReview = content.get(content.size() - 1);
            nextCursorScore = lastReview.getScore();
            nextCursorId = lastReview.getId();
        }

        return ReviewConverter.toMyReviewsByScoreCursorResponse(items, nextCursorScore, nextCursorId, hasNext);
    }
}
