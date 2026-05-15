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
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.entity.ReviewPhoto;
import com.example.umc10th.domain.review.repository.ReviewPhotoRepository;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
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
        Store store = storeRepository.findStoreById(storeId)
                .orElseThrow(() -> new StoreException(StoreErrorCode.STORE_NOT_FOUND));
        Review review = ReviewConverter.toReview(member, store, dto);
        reviewRepository.save(review);
    }

    public List<ReviewInfo> getReviewList(Long storeId) {
        Store store = storeRepository.findStoreById(storeId)
                .orElseThrow(() -> new StoreException(StoreErrorCode.STORE_NOT_FOUND));
        List<Review> reviewList = reviewRepository.getReviewsByStore(store);
        return reviewList.stream().map(ReviewConverter::toReviewInfo).toList();
    }

    public List<ReviewPhotoUrl> getReviewPhotoList(Long storeId) {
        Store store = storeRepository.findStoreById(storeId)
                .orElseThrow(() -> new StoreException(StoreErrorCode.STORE_NOT_FOUND));
        List<ReviewPhoto> reviewPhotoList = reviewPhotoRepository.getReviewPhotoByStore(storeId);
        return reviewPhotoList.stream().map(ReviewConverter::toReviewPhotoUrl).toList();
    }
}
