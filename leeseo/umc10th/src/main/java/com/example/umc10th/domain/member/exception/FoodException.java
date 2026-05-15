package com.example.umc10th.domain.member.exception;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import com.example.umc10th.global.apiPayload.exception.BaseException;

public class FoodException extends BaseException {
    public FoodException(BaseErrorCode code) {
        super(code);
    }
}
