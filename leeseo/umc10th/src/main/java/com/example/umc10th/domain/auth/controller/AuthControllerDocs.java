package com.example.umc10th.domain.auth.controller;

import com.example.umc10th.domain.auth.dto.AuthReqDto;
import com.example.umc10th.domain.auth.dto.AuthResDto;
import com.example.umc10th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthControllerDocs {

    @Operation(
            summary = "회원가입",
            description = "이메일 회원가입 진행"
    )
    public ApiResponse<AuthResDto.Id> signUp(
            @RequestBody @Valid AuthReqDto.Signup dto
    );

    @Operation(
            summary = "이메일 로그인",
            description = "이메일 로그인 진행"
    )
    public ApiResponse<AuthResDto.AccessToken> login(
            @RequestBody @Valid AuthReqDto.Login dto
    );
}
