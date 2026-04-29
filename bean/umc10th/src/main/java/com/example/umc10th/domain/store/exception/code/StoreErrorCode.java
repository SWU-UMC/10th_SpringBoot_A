package com.example.umc10th.domain.store.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StoreErrorCode {
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "STORE404", "Store not found.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}