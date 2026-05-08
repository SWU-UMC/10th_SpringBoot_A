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
        Store store = storeRepository.findStoreById(storeId).orElseThrow(() -> new StoreException(StoreErrorCode.STORE_NOT_FOUND));
        Review review = ReviewConverter.toReview(member, store, dto);
        reviewRepository.save(review);
    }

    public ReviewResDto.Pagination<ReviewInfo> getReviewList(Long storeId, Integer pageSize, String cursor, String query) {
        if (cursor == null) cursor = "-1";
        PageRequest pageRequest = PageRequest.of(0, pageSize);
        long idCursor;
        double rateCursor;
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
                    rateCursor =  Double.parseDouble(cursorSplit[0]);
                    idCursor = Long.parseLong(cursorSplit[1]);
                    reviewList = reviewRepository.findReviewsByRateCursor(storeId, rateCursor, idCursor, pageRequest);
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
                    reviewList = reviewRepository.findReviewsOrderByRate(storeId, pageRequest);
                    break;
                default:
                    throw new ReviewException(ReviewErrorCode.INVALID_QUERY);
            }
        }
        nextCursor = "-1";

        if (!reviewList.isEmpty()) {

            Review lastReview = reviewList.getContent()
                    .get(reviewList.getNumberOfElements() - 1);

            switch (query.toLowerCase()) {

                case "id":
                    nextCursor = lastReview.getId() + ":" + lastReview.getId();
                    break;

                case "rate":
                    nextCursor = lastReview.getRate() + ":" + lastReview.getId();
                    break;
            }
        }

        return ReviewConverter.toPagination(
                reviewList.map(ReviewConverter::toReviewInfo).toList(),
                reviewList.hasNext(),
                nextCursor,
                reviewList.getSize());
    }

    public ReviewResDto.Pagination<ReviewPhotoUrl> getReviewPhotoList(Long storeId, Integer pageSize, String cursor, String query) {
        if (cursor == null) cursor = "-1";
        PageRequest pageRequest = PageRequest.of(0, pageSize);

        long idCursor;
        double rateCursor;
        Slice<ReviewPhoto> reviewPhotoList;
        String nextCursor;

        if (!cursor.equals("-1")){
            String[] cursorSplit = cursor.split(":");
            switch (query.toLowerCase()){
                case "id":
                    idCursor = Long.parseLong(cursorSplit[1]);
                    reviewPhotoList = reviewPhotoRepository.findReviewPhotosByStore_IdAndIdLessThanOrderByIdDesc(storeId, idCursor, pageRequest);
                    break;
                case "rate":
                    rateCursor = Double.parseDouble(cursorSplit[0]);
                    idCursor = Long.parseLong(cursorSplit[1]);
                    reviewPhotoList = reviewPhotoRepository.findReviewPhotosByRateCursor(storeId, rateCursor, idCursor, pageRequest);
                    break;
                default:
                    throw new ReviewException(ReviewErrorCode.INVALID_QUERY);
            }
        } else {
            switch (query.toLowerCase()){
                case "id":
                    reviewPhotoList = reviewPhotoRepository.findReviewPhotosByStore_IdOrderByIdDesc(storeId, pageRequest);
                    break;
                case "rate":
                    reviewPhotoList = reviewPhotoRepository.findReviewPhotosOrderByRate(storeId, pageRequest);
                    break;
                default:
                    throw new ReviewException(ReviewErrorCode.INVALID_QUERY);
            }
        }
        nextCursor = "-1";

        if (!reviewPhotoList.isEmpty()) {

            ReviewPhoto lastReviewPhoto = reviewPhotoList.getContent()
                    .get(reviewPhotoList.getNumberOfElements() - 1);

            switch (query.toLowerCase()) {

                case "id":
                    nextCursor = lastReviewPhoto.getId() + ":" + lastReviewPhoto.getId();
                    break;

                case "rate":
                    nextCursor = lastReviewPhoto.getReview().getRate() + ":" + lastReviewPhoto.getId();
                    break;
            }
        }

        return ReviewConverter.toPagination(
                reviewPhotoList.map(ReviewConverter::toReviewPhotoUrl).toList(),
                reviewPhotoList.hasNext(),
                nextCursor,
                reviewPhotoList.getSize());
    }
}
