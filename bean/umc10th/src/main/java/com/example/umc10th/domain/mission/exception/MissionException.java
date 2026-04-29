package com.example.umc10th.domain.mission.exception;

import com.example.umc10th.domain.mission.exception.code.MissionErrorCode;
import lombok.Getter;

@Getter
public class MissionException extends RuntimeException {

    private final MissionErrorCode errorCode;

    public MissionException(MissionErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}