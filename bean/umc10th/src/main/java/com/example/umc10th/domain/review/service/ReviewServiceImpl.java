package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.review.converter.ReviewConverter;
import com.example.umc10th.domain.review.dto.ReviewReqDto;
import com.example.umc10th.domain.review.dto.ReviewResDto;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.exception.ReviewException;
import com.example.umc10th.domain.review.exception.code.ReviewErrorCode;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import com.example.umc10th.domain.store.entity.Store;
import com.example.umc10th.domain.store.exception.StoreException;
import com.example.umc10th.domain.store.exception.code.StoreErrorCode;
import com.example.umc10th.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
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
        Member member = getMember(request.memberId());

        Store store = storeRepository.findById(request.storeId())
                .orElseThrow(() -> new StoreException(StoreErrorCode.STORE_NOT_FOUND));

        Review savedReview = reviewRepository.save(ReviewConverter.toEntity(request, member, store));
        return ReviewConverter.toReviewInfo(savedReview);
    }

    @Override
    public ReviewResDto.MyReviewsByIdCursorResponse getMyReviewsByIdCursor(ReviewReqDto.MyReviewsByIdCursorRequest request) {
        getMember(request.memberId());

        PageRequest pageRequest = PageRequest.of(0, request.size());

        Slice<Review> reviewSlice = request.cursorId() == null
                ? reviewRepository.findMyReviewsByIdCursorFirst(request.memberId(), pageRequest)
                : reviewRepository.findMyReviewsByIdCursorNext(request.memberId(), request.cursorId(), pageRequest);

        return ReviewConverter.toMyReviewsByIdCursorResponse(reviewSlice);
    }

    @Override
    public ReviewResDto.MyReviewsByScoreCursorResponse getMyReviewsByScoreCursor(ReviewReqDto.MyReviewsByScoreCursorRequest request) {
        getMember(request.memberId());

        boolean bothCursorProvided = request.cursorScore() != null && request.cursorId() != null;
        boolean bothCursorEmpty = request.cursorScore() == null && request.cursorId() == null;
        if (!bothCursorProvided && !bothCursorEmpty) {
            throw new ReviewException(ReviewErrorCode.INVALID_CURSOR);
        }

        PageRequest pageRequest = PageRequest.of(0, request.size());

        Slice<Review> reviewSlice = bothCursorEmpty
                ? reviewRepository.findMyReviewsByScoreCursorFirst(request.memberId(), pageRequest)
                : reviewRepository.findMyReviewsByScoreCursorNext(
                request.memberId(),
                request.cursorScore(),
                request.cursorId(),
                pageRequest
        );

        return ReviewConverter.toMyReviewsByScoreCursorResponse(reviewSlice);
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
    }
}
