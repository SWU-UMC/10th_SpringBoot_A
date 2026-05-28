package com.example.umc10th.domain.auth.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthSuccessCode implements BaseSuccessCode {

    SIGNUP_OK(HttpStatus.OK,
            "AUTH200_1",
            "회원가입 되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
