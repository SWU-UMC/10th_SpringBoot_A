package com.example.umc10th.domain.review.controller;

import com.example.umc10th.domain.review.dto.ReviewInfo;
import com.example.umc10th.domain.review.dto.ReviewPhotoUrl;
import com.example.umc10th.domain.review.dto.ReviewReqDto;
import com.example.umc10th.domain.review.exception.code.ReviewSuccessCode;
import com.example.umc10th.domain.review.service.ReviewService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Review", description = "리뷰 관련 API")
public class ReviewController implements ReviewControllerDocs{

    private final ReviewService reviewService;

    @PostMapping("/stores/{storeId}/reviews")
    public ApiResponse<Void> saveReview(
            @PathVariable Long storeId,
            @RequestBody ReviewReqDto.Review dto,
            @RequestParam Long memberId
    ) {
        reviewService.saveReview(storeId, memberId, dto);
        return ApiResponse.onSuccess(ReviewSuccessCode.REVIEW_POST_OK, null);
    }

    @GetMapping("/stores/{storeId}/reviews")
    public ApiResponse<List<ReviewInfo>> getReviewList(
            @PathVariable Long storeId
    ) {
        List<ReviewInfo> response = reviewService.getReviewList(storeId);
        return ApiResponse.onSuccess(ReviewSuccessCode.REVIEW_LIST_GET_OK, response);
    }

    @GetMapping("/stores/{storeId}/reviews/photos")
    public ApiResponse<List<ReviewPhotoUrl>> getReviewPhotoList(
            @PathVariable Long storeId
    ) {
        List<ReviewPhotoUrl> response = reviewService.getReviewPhotoList(storeId);
        return ApiResponse.onSuccess(ReviewSuccessCode.REVIEW_PHOTO_LIST_GET_OK, response);
    }

}
