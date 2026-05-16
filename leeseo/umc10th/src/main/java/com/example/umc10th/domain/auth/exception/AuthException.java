package com.example.umc10th.domain.auth.exception;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import com.example.umc10th.global.apiPayload.exception.BaseException;

public class AuthException extends BaseException {
    public AuthException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
