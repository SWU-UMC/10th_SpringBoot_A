package com.example.umc10th.domain.user.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserSuccessCode implements BaseSuccessCode {

    USER_JOIN_SUCCESS(HttpStatus.OK, "USER200_1", "회원가입에 성공했습니다."),
    USER_LOGIN_SUCCESS(HttpStatus.OK, "USER200_2", "로그인에 성공했습니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
