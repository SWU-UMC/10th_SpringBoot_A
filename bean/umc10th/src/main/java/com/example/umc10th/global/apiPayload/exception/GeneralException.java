package com.example.umc10th.global.apiPayload.exception;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import com.example.umc10th.global.apiPayload.code.dto.ReasonDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GeneralException extends RuntimeException {

    private final BaseErrorCode code;

    public ReasonDto getErrorReason() {
        return code.getReason();
    }

    public ReasonDto getErrorReasonHttpStatus() {
        return code.getReasonHttpStatus();
    }
}