package com.example.umc10th.domain.mission.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MissionSuccessCode implements BaseSuccessCode {

    MISSION_LIST_READ(HttpStatus.OK, "MISSION200_1", "미션 목록 조회 성공"),
    MISSION_SUCCESS_SUMMARY_READ(HttpStatus.OK, "MISSION200_2", "미션 성공 정보를 성공적으로 조회했습니다."),
    MISSION_SUCCESS_REQUEST(HttpStatus.OK, "MISSION200_3", "미션 성공 요청이 완료되었습니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
