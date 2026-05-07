package com.example.umc10th.domain.mission.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MissionSuccessCode implements BaseSuccessCode {
    ACHIEVEMENT_GET_OK(HttpStatus.OK,
            "MISSION200_1",
            "미션 달성률이 조회되었습니다."),

    MISSION_LIST_GET_OK(HttpStatus.OK,
            "MISSION200_2",
            "미션 목록이 조회되었습니다."),

    MEMBER_MISSION_POST_OK(HttpStatus.OK,
            "MISSION200_3",
            "미션 도전이 생성되었습니다."),

    MY_MISSION_LIST_GET_OK(HttpStatus.OK,
            "MISSION200_4",
            "내 미션 목록이 조회되었습니다."),

    MISSION_SUCCESS_REQUEST_OK(HttpStatus.OK,
            "MISSION200_5",
            "미션 성공 요청이 저장되었습니다."),

    OWNER_NUMBER_GET_OK(HttpStatus.OK,
            "MISSION200_6",
                    "사장님 구분번호가 조회되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
