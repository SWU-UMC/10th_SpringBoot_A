package com.example.umc10th.global.apiPayload.code;

import com.example.umc10th.global.apiPayload.code.dto.ReasonDto;

public interface BaseCode {
    ReasonDto getReason();

    ReasonDto getReasonHttpStatus();
}