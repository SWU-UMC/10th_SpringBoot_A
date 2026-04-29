package com.example.umc10th.domain.mission.exception;

import com.example.umc10th.domain.mission.exception.code.MissionErrorCode;
import com.example.umc10th.global.apiPayload.exception.GeneralException;

public class MissionException extends GeneralException {
    public MissionException(MissionErrorCode errorCode) {
        super(errorCode);
    }
}