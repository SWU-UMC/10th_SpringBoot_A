package com.example.umc10th.domain.mission.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MissionErrorCode implements BaseErrorCode {

    MISSION_NOT_FOUND("MISSION_404", "존재하지 않는 미션입니다.", HttpStatus.NOT_FOUND),
    USER_MISSION_NOT_FOUND("USER_MISSION_404", "존재하지 않는 도전 미션입니다.", HttpStatus.NOT_FOUND),
    ALREADY_CHALLENGING("MISSION_409_CHALLENGING", "이미 도전 중이거나 완료한 미션입니다.", HttpStatus.CONFLICT),
    ALREADY_COMPLETED("MISSION_409_COMPLETED", "이미 완료된 미션입니다.", HttpStatus.CONFLICT),
    INVALID_MISSION_STATUS("MISSION_400_STATUS", "유효하지 않은 미션 상태입니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
