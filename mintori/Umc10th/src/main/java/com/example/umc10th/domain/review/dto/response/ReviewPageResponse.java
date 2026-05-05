package com.example.umc10th.domain.review.dto.response;

import java.util.List;

public record ReviewPageResponse(
        List<ReviewResponse> reviews,
        Integer page,
        Integer size,
        Long totalElements,
        Integer totalPages,
        Boolean hasNext
) {
}
