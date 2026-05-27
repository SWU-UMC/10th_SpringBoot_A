package com.example.umc10th.domain.auth.service;

import com.example.umc10th.domain.auth.dto.AuthRequestDto;
import com.example.umc10th.domain.auth.dto.AuthResponseDto;

public interface AuthService {

    AuthResponseDto.SignUpResultDto signUp(AuthRequestDto.SignUpDto request);
}
