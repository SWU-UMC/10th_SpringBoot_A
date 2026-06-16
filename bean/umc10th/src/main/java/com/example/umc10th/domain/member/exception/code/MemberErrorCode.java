package com.example.umc10th.domain.member.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import com.example.umc10th.global.apiPayload.code.dto.ReasonDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER404_1", "User not found."),
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "MEMBER400_1", "Email already exists."),
    FOOD_CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER400_2", "Food category not found."),
    REQUIRED_TERMS_NOT_AGREED(HttpStatus.BAD_REQUEST, "MEMBER400_3", "Required terms must be agreed."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "MEMBER401_1", "Password does not match.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDto getReason() {
        return new ReasonDto(httpStatus, false, code, message);
    }

    @Override
    public ReasonDto getReasonHttpStatus() {
        return getReason();
    }
}
