package com.example.umc10th.domain.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class ReviewReqDto {

    @Schema(name = "Review", description = "등록할 리뷰 정보를 저장합합니다.")
    public record Review(
            @NotNull
            Long rate,
            @NotBlank
            String content,
            @NotNull
            List<ReviewPhoto> reviewPhotoList
    ) {}

    @Schema(name = "ReviewPhoto", description = "등록할 리뷰 사진들을 저장합합니다.")
    public record ReviewPhoto(
            @NotBlank
            String  reviewPhoto
    ) {}
}
