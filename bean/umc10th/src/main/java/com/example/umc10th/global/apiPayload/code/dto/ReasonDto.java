package com.example.umc10th.global.apiPayload.code.dto;

import org.springframework.http.HttpStatus;

public record ReasonDto(
        HttpStatus httpStatus,
        boolean isSuccess,
        String code,
        String message
) {
}