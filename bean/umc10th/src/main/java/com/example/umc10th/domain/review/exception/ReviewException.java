package com.example.umc10th.domain.review.exception;

import com.example.umc10th.domain.review.exception.code.ReviewErrorCode;
import lombok.Getter;

@Getter
public class ReviewException extends RuntimeException {

    private final ReviewErrorCode errorCode;

    public ReviewException(ReviewErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}