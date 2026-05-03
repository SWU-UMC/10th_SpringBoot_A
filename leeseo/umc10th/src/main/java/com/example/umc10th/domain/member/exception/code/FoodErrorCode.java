package com.example.umc10th.domain.member.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FoodErrorCode implements BaseErrorCode {
    FOOD_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND,
            "FOOD404_1",
            "해당 음식 타입이 존재하지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
