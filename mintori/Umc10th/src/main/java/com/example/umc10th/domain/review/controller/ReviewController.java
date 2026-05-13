package com.example.umc10th.domain.review.controller;

import com.example.umc10th.domain.review.dto.request.MyReviewCursorRequest;
import com.example.umc10th.domain.review.dto.request.ReviewRequest;
import com.example.umc10th.domain.review.dto.response.MyReviewCursorResponse;
import com.example.umc10th.domain.review.dto.response.ReviewPageResponse;
import com.example.umc10th.domain.review.dto.response.ReviewResponse;
import com.example.umc10th.domain.review.service.ReviewService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.GeneralSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ApiResponse<ReviewResponse> writeReview(@Valid @RequestBody ReviewRequest request) {
        return ApiResponse.onSuccess(GeneralSuccessCode.CREATED, reviewService.writeReview(request));
    }

    // 기존 오프셋 기반 내 리뷰 조회 API
    @GetMapping("/me")
    public ApiResponse<ReviewPageResponse> getMyReviews(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.onSuccess(
                GeneralSuccessCode.OK,
                reviewService.getMyReviews(userId, page, size)
        );
    }


    // 미션 미션과 동일한 컨벤션 (사용자 ID를 Request Body로 받기, 하드코딩 금지) 을 유지하기 위해서 추가한 코드
    @PostMapping("/me")
    public ApiResponse<MyReviewCursorResponse> getMyReviewsByCursor(
            @Valid @RequestBody MyReviewCursorRequest request
    ) {
        return ApiResponse.onSuccess(
                GeneralSuccessCode.OK,
                reviewService.getMyReviewsByCursor(request)
        );
    }

    @GetMapping("/stores/{storeId}")
    public ApiResponse<ReviewPageResponse> getStoreReviews(
            @PathVariable Long storeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.onSuccess(
                GeneralSuccessCode.OK,
                reviewService.getStoreReviews(storeId, page, size)
        );
    }
}
