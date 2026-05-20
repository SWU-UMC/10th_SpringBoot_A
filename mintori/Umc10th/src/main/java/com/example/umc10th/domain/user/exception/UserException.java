package com.example.umc10th.domain.user.exception;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import com.example.umc10th.global.apiPayload.exception.BaseException;

public class UserException extends BaseException {
    public UserException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
