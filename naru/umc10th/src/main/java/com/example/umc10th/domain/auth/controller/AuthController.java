package com.example.umc10th.domain.auth.controller;

import com.example.umc10th.domain.auth.dto.AuthRequestDto;
import com.example.umc10th.domain.auth.dto.AuthResponseDto;
import com.example.umc10th.domain.auth.service.AuthService;
import com.example.umc10th.domain.user.exception.code.UserSuccessCode;
import com.example.umc10th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "인증 API", description = "회원가입 및 로그인 관련 API")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입 API")
    @PostMapping("/signup")
    public ApiResponse<AuthResponseDto.SignUpResultDto> signUp(
            @RequestBody @Valid AuthRequestDto.SignUpDto request
    ) {
        AuthResponseDto.SignUpResultDto result = authService.signUp(request);
        return ApiResponse.onSuccess(UserSuccessCode.USER_JOIN_SUCCESS, result);
    }
}
