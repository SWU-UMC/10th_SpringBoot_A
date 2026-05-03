package com.example.umc10th.domain.review.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ReviewInfo(
        double rate,
        String content,
        List<ReviewPhotoUrl> reviewPhotoList,
        String reviewerNickname,
        LocalDateTime createdAt
) {}