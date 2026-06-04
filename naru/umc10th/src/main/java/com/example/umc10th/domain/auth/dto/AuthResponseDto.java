package com.example.umc10th.domain.auth.dto;

import lombok.Builder;

public class AuthResponseDto {

    @Builder
    public record SignUpResultDto(
            Long userId,
            String email,
            String name
    ) {}

    @Builder
    public record LoginResultDto(
            String grantType,
            String accessToken,
            String refreshToken,
            Long accessTokenExpiresIn
    ) {}
}
