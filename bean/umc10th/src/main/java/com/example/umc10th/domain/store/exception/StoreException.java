package com.example.umc10th.domain.store.exception;

import com.example.umc10th.domain.store.exception.code.StoreErrorCode;
import com.example.umc10th.global.apiPayload.exception.GeneralException;

public class StoreException extends GeneralException {
    public StoreException(StoreErrorCode errorCode) {
        super(errorCode);
    }
}