package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.dto.MemberReqDto;
import com.example.umc10th.domain.member.dto.MemberResDto;
import com.example.umc10th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc10th.domain.member.service.MemberService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.security.AuthMember;
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
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/api/v1/users/signup")
    public ResponseEntity<ApiResponse<MemberResDto.SignUpResponse>> signUp(
            @Valid @RequestBody MemberReqDto.SignUpRequest request
    ) {
        MemberResDto.SignUpResponse result = memberService.signUp(request);
        return ResponseEntity.ok(ApiResponse.of(MemberSuccessCode.SIGN_UP_SUCCESS, result));
    }

    @PostMapping("/api/v1/users/login")
    public ResponseEntity<ApiResponse<MemberResDto.LoginResponse>> login(
            @Valid @RequestBody MemberReqDto.LoginRequest request
    ) {
        MemberResDto.LoginResponse result = memberService.login(request);
        return ResponseEntity.ok(ApiResponse.of(MemberSuccessCode.LOGIN_SUCCESS, result));
    }

    @PostMapping("/api/v1/users/me")
    public ResponseEntity<ApiResponse<MemberResDto.GetMyPageResponse>> getMyPage(
            @Valid @RequestBody MemberReqDto.GetMyPageRequest request
    ) {
        MemberResDto.GetMyPageResponse result = memberService.getMyPage(request.id());

        return ResponseEntity.ok(ApiResponse.of(MemberSuccessCode.GET_MY_PAGE_SUCCESS, result));
    }

    @GetMapping("/api/v2/users/me")
    public ResponseEntity<ApiResponse<MemberResDto.GetMyPageResponse>> getMyPageV2(
            @AuthenticationPrincipal AuthMember authMember
    ) {
        MemberResDto.GetMyPageResponse result = memberService.getMyPage(authMember.getMember());

        return ResponseEntity.ok(ApiResponse.of(MemberSuccessCode.GET_MY_PAGE_SUCCESS, result));
    }
}
