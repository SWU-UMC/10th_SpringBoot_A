package com.example.umc10th.domain.review.dto;

import lombok.Builder;

@Builder
public record ReviewPhotoUrl(
        Long reviewPhotoId,
        Double reviewRate,
        String  reviewPhoto
) {}
