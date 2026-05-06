package com.example.umc10th.domain.mission.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MissionErrorCode implements BaseErrorCode {

    MISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "MISSION404_1", "존재하지 않는 미션 기록입니다."),
    MISSION_ALREADY_COMPLETED(HttpStatus.BAD_REQUEST, "MISSION400_1", "이미 완료된 미션입니다."),
    MISSION_NOT_CHALLENGING(HttpStatus.BAD_REQUEST, "MISSION400_2", "도전 중인 미션이 아닙니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
