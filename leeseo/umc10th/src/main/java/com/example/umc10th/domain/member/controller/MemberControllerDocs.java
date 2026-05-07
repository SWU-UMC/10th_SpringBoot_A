package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.dto.MemberReqDto;
import com.example.umc10th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface MemberControllerDocs {

    @Operation(
            summary = "약관 동의 저장",
            description = "약관 동의 여부를 저장합니다."
    )
    public ApiResponse<Void> saveTermAgreement(
            @RequestBody MemberReqDto.TermList dto
    );

    @Operation(
            summary = "회원정보 저장",
            description = "회원 프로필 정보를 저장합니다."
    )
    public ApiResponse<Void> updateProfile(
            @RequestBody MemberReqDto.Profile dto
    );

    @Operation(
            summary = "닉네임 수정",
            description = "닉네임을 수정합니다."
    )
    @PatchMapping("/me/nickname")
    public ApiResponse<Void> updateNickname(
            @RequestBody MemberReqDto.Nickname dto
    );
}
