package com.example.umc10th.domain.review.exception;

import com.example.umc10th.domain.review.exception.code.ReviewErrorCode;
import com.example.umc10th.global.apiPayload.exception.GeneralException;

public class ReviewException extends GeneralException {
    public ReviewException(ReviewErrorCode errorCode) {
        super(errorCode);
    }
}