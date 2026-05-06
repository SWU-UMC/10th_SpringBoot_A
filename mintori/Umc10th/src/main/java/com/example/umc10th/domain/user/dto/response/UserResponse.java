package com.example.umc10th.domain.user.dto.response;

import java.time.LocalDateTime;

public record UserResponse(
        Long userId,
        LocalDateTime createdAt
) {
}
