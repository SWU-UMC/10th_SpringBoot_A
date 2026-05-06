package com.example.umc10th.domain.review.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {

    REVIEW_NOT_FOUND("REVIEW_404", "존재하지 않는 리뷰입니다.", HttpStatus.NOT_FOUND),
    INVALID_REVIEW_SCORE("REVIEW_400_SCORE", "별점은 0.0 ~ 5.0 사이여야 합니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
