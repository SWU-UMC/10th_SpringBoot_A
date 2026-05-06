package com.example.umc10th.domain.store.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum StoreErrorCode implements BaseErrorCode {

    STORE_NOT_FOUND("STORE_404", "존재하지 않는 가게입니다.", HttpStatus.NOT_FOUND),
    REGION_NOT_FOUND("REGION_404", "존재하지 않는 지역입니다.", HttpStatus.NOT_FOUND),
    INVALID_STORE_REQUEST("STORE_400", "가게 요청이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
