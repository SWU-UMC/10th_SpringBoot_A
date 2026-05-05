package com.example.umc10th.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GeneralErrorCode implements BaseErrorCode {
    INTERNAL_SERVER_ERROR("COMMON_500", "서버 에러, 관리자에게 문의 바랍니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST("COMMON_400", "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED("COMMON_401", "인증이 필요합니다.", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("COMMON_403", "금지된 요청입니다.", HttpStatus.FORBIDDEN),
    NOT_FOUND("COMMON_404", "요청한 페이지를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;

}
