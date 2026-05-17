package com.example.umc10th.domain.store.exception;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import com.example.umc10th.global.apiPayload.exception.BaseException;

public class StoreException extends BaseException {
    public StoreException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
