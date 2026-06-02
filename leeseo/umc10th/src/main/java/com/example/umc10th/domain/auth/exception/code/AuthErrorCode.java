package com.example.umc10th.domain.auth.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements BaseErrorCode {
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST,
            "AUTH400_1",
            "잘못된 비밀번호입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
