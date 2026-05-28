package com.example.umc10th.domain.auth.controller;

import com.example.umc10th.domain.auth.dto.AuthReqDto;
import com.example.umc10th.domain.auth.dto.AuthResDto;
import com.example.umc10th.domain.auth.exception.code.AuthSuccessCode;
import com.example.umc10th.domain.auth.service.AuthService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth", description = "인증 관련 API")
public class AuthController implements AuthControllerDocs{

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ApiResponse<AuthResDto.Id> signUp(
        @RequestBody @Valid AuthReqDto.Signup dto
    ) {
        return ApiResponse.onSuccess(AuthSuccessCode.SIGNUP_OK, authService.signUp(dto));
    }
}
