package com.example.umc10th.domain.review.controller;

import com.example.umc10th.domain.review.dto.ReviewReqDto;
import com.example.umc10th.domain.review.dto.ReviewResDto;
import com.example.umc10th.domain.review.service.ReviewService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResDto.ReviewInfo>> getReview(@PathVariable Long reviewId) {
        return ResponseEntity.ok(ApiResponse.onSuccess(reviewService.getReview(reviewId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ReviewResDto.ReviewInfo>> createReview(@Valid @RequestBody ReviewReqDto.Create request) {
        return ResponseEntity.ok(ApiResponse.onSuccess(reviewService.createReview(request)));
    }
}
