package com.example.umc10th.domain.member.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode {
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER404", "����ڸ� ã�� �� �����ϴ�.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}