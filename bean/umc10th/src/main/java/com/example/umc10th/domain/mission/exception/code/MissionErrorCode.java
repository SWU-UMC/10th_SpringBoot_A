package com.example.umc10th.domain.mission.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MissionErrorCode {
    MISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "MISSION404", "�̼��� ã�� �� �����ϴ�."),
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "STORE404", "���Ը� ã�� �� �����ϴ�.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}