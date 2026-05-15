package com.example.umc10th.domain.mission.exception;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import com.example.umc10th.global.apiPayload.exception.BaseException;

public class StoreException extends BaseException {
    public StoreException(BaseErrorCode code) {
        super(code);
    }
}
