package com.example.umc10th.domain.user.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements BaseErrorCode {

    USER_NOT_FOUND("USER_404", "존재하지 않는 유저입니다.", HttpStatus.NOT_FOUND),
    DUPLICATE_EMAIL("USER_409", "이미 가입된 이메일입니다.", HttpStatus.CONFLICT),
    INVALID_USER_REQUEST("USER_400", "유저 요청이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
