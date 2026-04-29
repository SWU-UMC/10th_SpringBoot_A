package com.example.umc10th.domain.review.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewErrorCode {
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW404", "���並 ã�� �� �����ϴ�."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER404", "����ڸ� ã�� �� �����ϴ�."),
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "STORE404", "���Ը� ã�� �� �����ϴ�.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}