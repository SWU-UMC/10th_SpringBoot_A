package com.example.umc10th.domain.review.dto;

import lombok.Builder;

import java.util.List;

public class ReviewResDto {

    @Builder
    public record Pagination<T>(
            List<T> data,
            Boolean hasNext,
            String nextCursor,
            Integer pageSize
    ){}
}
