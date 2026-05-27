package com.example.umc10th.domain.review.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {
    INVALID_QUERY(HttpStatus.BAD_REQUEST,
            "REVIEW400_1",
            "잘못된 쿼리 입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
