package com.example.umc10th.domain.review.controller;

import com.example.umc10th.domain.review.dto.ReviewRequestDto;
import com.example.umc10th.domain.review.dto.ReviewResponseDto;
import com.example.umc10th.domain.review.exception.code.ReviewSuccessCode;
import com.example.umc10th.domain.review.service.ReviewService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
@Tag(name = "리뷰 API", description = "리뷰 작성 API")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "리뷰 작성 API")
    @PostMapping
    public ResponseEntity<ApiResponse<ReviewResponseDto.CreateReviewResultDto>> createReview(
            @RequestParam Long userMissionId,
            @RequestBody @Valid ReviewRequestDto.CreateReviewDto request
    ) {
        ReviewResponseDto.CreateReviewResultDto result = reviewService.createReview(userMissionId, request);
        ReviewSuccessCode code = ReviewSuccessCode.REVIEW_CREATED;
        return ResponseEntity.status(code.getStatus())
                .body(ApiResponse.onSuccess(code, result));
    }
}
