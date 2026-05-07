package com.example.umc10th.domain.review.dto;

import java.time.LocalDate;
import java.util.List;

public class ReviewResDto {

    public record Review(
            Long rate,
            String content,
            List<ReviewReqDto.ReviewPhoto> reviewPhotoList,
            String reviewerNickname,
            LocalDate createdAt
    ) {}

    public record ReviewPhoto(
            String  reviewPhoto
    ) {}

}
