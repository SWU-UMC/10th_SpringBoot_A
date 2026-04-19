package com.example.umc10th.domain.user.converter;

import com.example.umc10th.domain.user.dto.UserResponseDto;

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
}
