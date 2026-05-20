package com.example.umc10th.domain.auth.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements BaseErrorCode {

    TERM_NOT_FOUND(HttpStatus.NOT_FOUND, "AUTH404_1", "약관을 찾을 수 없습니다."),
    REQUIRED_TERM_NOT_AGREED(HttpStatus.BAD_REQUEST, "AUTH400_1", "필수 약관에 동의해야 합니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
