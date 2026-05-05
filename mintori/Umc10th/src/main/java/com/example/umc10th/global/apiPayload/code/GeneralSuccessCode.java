package com.example.umc10th.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GeneralSuccessCode implements BaseSuccessCode{

    OK("COMMON_200", "성공입니다.", HttpStatus.OK),
    CREATED("COMMON_201", "생성되었습니다.", HttpStatus.CREATED);

    public final String code;
    public final String message;
    public final HttpStatus status;
}
