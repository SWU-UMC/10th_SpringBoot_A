package com.example.umc10th.domain.member.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import com.example.umc10th.global.apiPayload.code.dto.ReasonDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberSuccessCode implements BaseSuccessCode {
    GET_MY_PAGE_SUCCESS(HttpStatus.OK, "MEMBER2001", "성공적으로 사용자 정보를 조회했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDto getReason() {
        return new ReasonDto(httpStatus, true, code, message);
    }

    @Override
    public ReasonDto getReasonHttpStatus() {
        return getReason();
    }
}