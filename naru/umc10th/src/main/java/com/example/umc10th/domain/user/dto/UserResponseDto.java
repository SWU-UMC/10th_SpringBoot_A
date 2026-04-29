package com.example.umc10th.domain.user.dto;

import lombok.Builder;

public class UserResponseDto {

    @Builder
    public record RequestBody(
            String stringTest,
            Long longTest
    ) {}

    @Builder
    public record MyPageResultDto(
            String name,
            String email,
            String phoneNumber,
            String profileImageKey,
            Boolean isVerified,
            Integer totalPoint
    ) {}
}
