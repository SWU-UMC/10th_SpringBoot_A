package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.dto.MemberReqDto;
import com.example.umc10th.domain.member.dto.MemberResDto;
import com.example.umc10th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc10th.domain.member.service.MemberService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.security.AuthMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Member", description = "회원 API")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원가입", description = "회원 정보를 저장하고 JWT access token을 발급합니다.")
    @PostMapping("/api/v1/users/signup")
    public ResponseEntity<ApiResponse<MemberResDto.SignUpResponse>> signUp(
            @Valid @RequestBody MemberReqDto.SignUpRequest request
    ) {
        MemberResDto.SignUpResponse result = memberService.signUp(request);
        return ResponseEntity.ok(ApiResponse.of(MemberSuccessCode.SIGN_UP_SUCCESS, result));
    }

    @Operation(summary = "로그인", description = "이메일과 비밀번호를 검증하고 JWT access token을 발급합니다.")
    @PostMapping("/api/v1/users/login")
    public ResponseEntity<ApiResponse<MemberResDto.LoginResponse>> login(
            @Valid @RequestBody MemberReqDto.LoginRequest request
    ) {
        MemberResDto.LoginResponse result = memberService.login(request);
        return ResponseEntity.ok(ApiResponse.of(MemberSuccessCode.LOGIN_SUCCESS, result));
    }

    @Operation(summary = "마이페이지 조회(v1)", description = "요청 body의 회원 ID로 마이페이지 정보를 조회합니다.")
    @PostMapping("/api/v1/users/me")
    public ResponseEntity<ApiResponse<MemberResDto.GetMyPageResponse>> getMyPage(
            @Valid @RequestBody MemberReqDto.GetMyPageRequest request
    ) {
        MemberResDto.GetMyPageResponse result = memberService.getMyPage(request.id());

        return ResponseEntity.ok(ApiResponse.of(MemberSuccessCode.GET_MY_PAGE_SUCCESS, result));
    }

    @Operation(
            summary = "마이페이지 조회(v2)",
            description = "Authorization 헤더의 Bearer 토큰으로 인증된 회원의 마이페이지 정보를 조회합니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping("/api/v2/users/me")
    public ResponseEntity<ApiResponse<MemberResDto.GetMyPageResponse>> getMyPageV2(
            @AuthenticationPrincipal AuthMember authMember
    ) {
        MemberResDto.GetMyPageResponse result = memberService.getMyPage(authMember.getMember());

        return ResponseEntity.ok(ApiResponse.of(MemberSuccessCode.GET_MY_PAGE_SUCCESS, result));
    }
}
