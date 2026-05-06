package com.example.umc10th.domain.review.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {

    USER_MISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW404_1", "존재하지 않는 사용자 미션입니다."),
    USER_MISSION_OWNER_MISMATCH(HttpStatus.BAD_REQUEST, "REVIEW400_1", "해당 사용자의 미션이 아닙니다."),
    REVIEW_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "REVIEW400_2", "이미 리뷰를 작성한 미션입니다."),
    USER_MISSION_NOT_SUCCESS(HttpStatus.BAD_REQUEST, "REVIEW400_3", "성공한 미션에만 리뷰를 작성할 수 있습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
