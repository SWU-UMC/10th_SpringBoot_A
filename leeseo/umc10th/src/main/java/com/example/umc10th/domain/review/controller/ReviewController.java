package com.example.umc10th.domain.review.controller;

import com.example.umc10th.domain.review.dto.ReviewReqDto;
import com.example.umc10th.domain.review.dto.ReviewResDto;
import com.example.umc10th.domain.review.exception.code.ReviewSuccessCode;
import com.example.umc10th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Review", description = "리뷰 관련 API")
public class ReviewController implements ReviewControllerDocs{

    @PostMapping("/stores/{storeId}/reviews")
    public ApiResponse<Void> saveReview(
            @PathVariable Long storeId,
            @RequestBody ReviewReqDto.Review dto
    ) {
        // 서비스 로직
        return ApiResponse.onSuccess(ReviewSuccessCode.REVIEW_POST_OK, null);
    }

    @GetMapping("/stores/{storeId}/reviews")
    public ApiResponse<List<ReviewResDto.Review>> getReviewList(
            @PathVariable Long storeId
    ) {
        // 서비스 로직
        List<ReviewReqDto.ReviewPhoto> photos = List.of(new ReviewReqDto.ReviewPhoto("sss"));
        List<ReviewResDto.Review> response = List.of(new ReviewResDto.Review((long) 4.5, "맛있어요", photos, "이서", LocalDate.now()));
        return ApiResponse.onSuccess(ReviewSuccessCode.REVIEW_LIST_GET_OK, response);
    }

    @GetMapping("/stores/{storeId}/reviews/photos")
    public ApiResponse<List<ReviewReqDto.ReviewPhoto>> getReviewPhotoList(
            @PathVariable Long storeId
    ) {
        // 서비스 로직
        List<ReviewReqDto.ReviewPhoto> response = List.of(new ReviewReqDto.ReviewPhoto("sss"));
        return ApiResponse.onSuccess(ReviewSuccessCode.REVIEW_PHOTO_LIST_GET_OK, response);
    }

}
