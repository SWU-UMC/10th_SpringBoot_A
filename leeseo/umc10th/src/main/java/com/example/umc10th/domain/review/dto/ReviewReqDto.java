package com.example.umc10th.domain.review.dto;

import java.util.List;

public class ReviewReqDto {

    public record Review(
        Long rate,
        String content,
        List<ReviewPhoto> reviewPhotoList
    ) {}

    public record ReviewPhoto(
        String  reviewPhoto
    ) {}
}
