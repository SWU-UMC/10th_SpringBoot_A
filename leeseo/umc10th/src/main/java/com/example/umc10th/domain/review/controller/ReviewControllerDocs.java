package com.example.umc10th.domain.review.controller;

import com.example.umc10th.domain.review.dto.ReviewInfo;
import com.example.umc10th.domain.review.dto.ReviewPhotoUrl;
import com.example.umc10th.domain.review.dto.ReviewReqDto;
import com.example.umc10th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ReviewControllerDocs {

    @Operation(
            summary = "리뷰 작성",
            description = "리뷰를 저장합니다."
    )
    public ApiResponse<Void> saveReview(
            @PathVariable Long storeId,
            @RequestBody ReviewReqDto.Review dto,
            @RequestParam Long memberId
    );

    @Operation(
            summary = "리뷰 목록 조회",
            description = "리뷰 목록을 조회합니다."
    )
    public ApiResponse<List<ReviewInfo>> getReviewList(
            @PathVariable Long storeId
    );

    @Operation(
            summary = "리뷰 사진 목록 조회",
            description = "리뷰 사진 목록을 조회합니다."
    )
    public ApiResponse<List<ReviewPhotoUrl>> getReviewPhotoList(
            @PathVariable Long storeId
    );
}
