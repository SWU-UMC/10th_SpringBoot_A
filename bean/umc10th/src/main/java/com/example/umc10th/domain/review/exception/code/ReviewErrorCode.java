package com.example.umc10th.domain.review.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import com.example.umc10th.global.apiPayload.code.dto.ReasonDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW404_1", "Review not found."),
    INVALID_CURSOR(HttpStatus.BAD_REQUEST, "REVIEW400_1", "Invalid cursor request.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDto getReason() {
        return new ReasonDto(httpStatus, false, code, message);
    }

    @Override
    public ReasonDto getReasonHttpStatus() {
        return getReason();
    }
}
