package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.mission.exception.StoreException;
import com.example.umc10th.domain.mission.exception.code.StoreErrorCode;
import com.example.umc10th.domain.mission.repository.StoreRepository;
import com.example.umc10th.domain.review.converter.ReviewConverter;
import com.example.umc10th.domain.review.dto.ReviewInfo;
import com.example.umc10th.domain.review.dto.ReviewPhotoUrl;
import com.example.umc10th.domain.review.dto.ReviewReqDto;
import com.example.umc10th.domain.review.dto.ReviewResDto;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.entity.ReviewPhoto;
import com.example.umc10th.domain.review.exception.ReviewException;
import com.example.umc10th.domain.review.exception.code.ReviewErrorCode;
import com.example.umc10th.domain.review.repository.ReviewPhotoRepository;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewPhotoRepository reviewPhotoRepository;

    @Transactional
    public void saveReview(Long storeId, Long memberId, ReviewReqDto.Review dto) {
        Member member = memberRepository.findMemberById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        Store store = getStoreById(storeId);
        Review review = ReviewConverter.toReview(member, store, dto);
        reviewRepository.save(review);
    }

    public ReviewResDto.Pagination<ReviewInfo> getReviewList(Long storeId, Integer pageSize, String cursor, String query) {
        PageRequest pageRequest = PageRequest.of(0, pageSize);
        if (cursor != null) cursor = "-1";

        long idCursor;
        Slice<Review> reviewList;
        String nextCursor;

        if (!cursor.equals("-1")){
            String[] cursorSplit = cursor.split(":");
            switch (query.toLowerCase()){
                case "id":
                    idCursor = Long.parseLong(cursorSplit[1]);
                    reviewList = reviewRepository.findReviewsByStore_IdAndIdLessThanOrderByIdDesc(storeId, idCursor, pageRequest);
                    break;
                case "rate":
                    idCursor = Long.parseLong(cursorSplit[1]);
                    reviewList = reviewRepository.findReviewsByStore_IdAndRateLessThanOrderByRateDesc(storeId, idCursor, pageRequest);
                    break;
                default:
                    throw new ReviewException(ReviewErrorCode.INVALID_QUERY);
            }
        } else {
            switch (query.toLowerCase()){
                case "id":
                    reviewList = reviewRepository.findReviewsByStore_IdOrderByIdDesc(storeId, pageRequest);
                    break;
                case "rate":
                    reviewList = reviewRepository.findReviewsByStore_IdOrderByRateDesc(storeId, pageRequest);
                    break;
                default:
                    throw new ReviewException(ReviewErrorCode.INVALID_QUERY);
            }
        }
        nextCursor = "-1";

        if (!reviewList.isEmpty()) {
            Long lastId = reviewList.getContent()
                    .get(reviewList.getNumberOfElements() - 1)
                    .getId();

            nextCursor = lastId + ":" + lastId;
        }
        return ReviewConverter.toPagination(
                reviewList.map(ReviewConverter::toReviewInfo).toList(),
                reviewList.hasNext(),
                nextCursor,
                reviewList.getSize());
    }

    private Store getStoreById(Long storeId) {
        Store store = storeRepository.findStoreById(storeId)
                .orElseThrow(() -> new StoreException(StoreErrorCode.STORE_NOT_FOUND));
        return store;
    }

    public List<ReviewPhotoUrl> getReviewPhotoList(Long storeId) {
        getStoreById(storeId);
        List<ReviewPhoto> reviewPhotoList = reviewPhotoRepository.getReviewPhotoByStore(storeId);
        return reviewPhotoList.stream().map(ReviewConverter::toReviewPhotoUrl).toList();
    }
}
