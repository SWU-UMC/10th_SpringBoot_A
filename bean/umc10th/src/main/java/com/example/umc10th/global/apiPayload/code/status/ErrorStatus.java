package com.example.umc10th.global.apiPayload.code.status;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import com.example.umc10th.global.apiPayload.code.dto.ReasonDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorStatus implements BaseErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "Server error."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "Bad request."),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "COMMON4001", "Validation failed.");

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