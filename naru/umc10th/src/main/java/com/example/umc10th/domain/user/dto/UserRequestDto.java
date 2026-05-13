package com.example.umc10th.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class UserRequestDto {

    // Request Body 예시
    public record RequestBody(
            @NotBlank(message = "문자열 값은 필수입니다.")
            String stringTest,
            @NotNull(message = "숫자 값은 필수입니다.")
            Long longTest
    ) {}

    // static class 예시
    @Getter
    public static class RequestBodyClass {
        private String stringTest;
        private Long longTest;
    }

    public record GetMyPageDto(
            @NotNull(message = "사용자 ID는 필수입니다.")
            Long userId
    ) {}
}
