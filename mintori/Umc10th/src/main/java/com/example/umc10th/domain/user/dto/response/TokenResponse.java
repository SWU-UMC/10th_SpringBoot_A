package com.example.umc10th.domain.user.dto.response;

public record TokenResponse(
        Long userId,
        String tokenType,
        String accessToken,
        Long expiresIn
) {
}
