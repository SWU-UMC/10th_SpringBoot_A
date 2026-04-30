package com.example.umc10th.global.apiPayload.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GeneralSuccessCode implements BaseErrorCode{

    OK(HttpStatus.OK,
            "SUCCESS200_1",
            "요청에 성공했습니다."),

    CREATED(HttpStatus.CREATED,
            "SUCCESS201_1",
            "리소스가 성공적으로 생성되었습니다."),

    NO_CONTENT(HttpStatus.NO_CONTENT,
            "SUCCESS204_1",
            "본문이 비어 있습니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;
}
