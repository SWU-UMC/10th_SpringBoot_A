package com.example.umc10th.domain.user.converter;

import com.example.umc10th.domain.user.dto.UserResponseDto;
import com.example.umc10th.domain.user.entity.User;

public class UserConverter {

    public static UserResponseDto.RequestBody toRequestBody(
            String stringTest,
            Long longTest
    ) {
        return UserResponseDto.RequestBody.builder()
                .stringTest(stringTest)
                .longTest(longTest)
                .build();
    }

    public static UserResponseDto.MyPageResultDto toMyPageResultDto(User user) {
        return UserResponseDto.MyPageResultDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .profileImageKey(user.getProfileImageKey())
                .isVerified(user.getIsVerified())
                .totalPoint(user.getTotalPoint())
                .build();
    }
}
