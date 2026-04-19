package com.example.umc10th.domain.user.dto;

import lombok.Getter;

public class UserRequestDto {

    // Request Body 예시
    public record RequestBody(
            String stringTest,
            Long longTest
    ) {}

    // static class 예시
    @Getter
    public static class RequestBodyClass {
        private String stringTest;
        private Long longTest;
    }
}
