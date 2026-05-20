package com.example.umc10th.global.apiPayload.code;

import org.springframework.http.HttpStatus;

public interface BaseSuccessCode {
    String getCode();
    String getMessage();
    HttpStatus getStatus();
}
