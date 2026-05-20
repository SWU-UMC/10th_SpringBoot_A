package com.example.umc10th.domain.user.dto.response;

import com.example.umc10th.domain.user.enums.Gender;

public record MyPageResponse(
        Long userId,
        String name,
        String email,
        Gender gender,
        String address,
        String phoneNumber,
        Long point,
        Long challengingMissionCount,
        Long completedMissionCount,
        Long reviewCount
) {
}
