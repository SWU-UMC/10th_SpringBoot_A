package com.example.umc10th.domain.user.dto;

import lombok.Builder;

public class UserResponseDto {

    @Builder
    public record RequestBody(
            String stringTest,
            Long longTest
    ) {}
}
