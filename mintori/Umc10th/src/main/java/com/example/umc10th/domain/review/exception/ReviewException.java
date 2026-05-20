package com.example.umc10th.domain.review.exception;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import com.example.umc10th.global.apiPayload.exception.BaseException;

public class ReviewException extends BaseException {
    public ReviewException(BaseErrorCode errorCode) {
        super(errorCode);}
}
