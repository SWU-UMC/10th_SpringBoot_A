package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.dto.MemberReqDto;
import com.example.umc10th.domain.member.dto.MemberResDto;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.security.entity.AuthMember;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface MemberControllerDocs {

    @Operation(
            summary = "약관 동의 저장",
            description = "약관 동의 여부를 저장합니다."
    )
    public ApiResponse<Void> saveTermAgreement(
            @RequestBody MemberReqDto.TermList dto
    );

    @Operation(
            summary = "내 프로필 조회",
            description = "내 프로필 정보를 조회합니다."
    )
    public ApiResponse<MemberResDto.Profile> getMyProfile(
            @AuthenticationPrincipal AuthMember member
    );

    @Operation(
            summary = "타인 프로필 조회",
            description = "회원 프로필 정보를 조회합니다."
    )
    public ApiResponse<MemberResDto.Profile> getProfile(
            @PathVariable Long id
    );

    @Operation(
            summary = "회원정보 저장",
            description = "회원 프로필 정보를 저장합니다."
    )
    public ApiResponse<Void> updateProfile(
            @RequestBody @Valid  MemberReqDto.Profile dto
    );

    @Operation(
            summary = "닉네임 수정",
            description = "닉네임을 수정합니다."
    )
    public ApiResponse<Void> updateNickname(
            @RequestBody @Valid  MemberReqDto.Nickname dto
    );
}
