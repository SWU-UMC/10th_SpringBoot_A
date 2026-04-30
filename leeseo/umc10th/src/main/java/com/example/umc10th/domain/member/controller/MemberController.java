package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.dto.MemberReqDto;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc10th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/vi/members")
@Tag(name = "Member", description = "회원 관련 API")
public class MemberController implements MemberControllerDocs{

    @PostMapping("/me/term")
    public ApiResponse<Void> saveTermAgreement(
            @RequestBody MemberReqDto.TermList dto
    ) {
        // 서비스 로직
        return ApiResponse.onSuccess(MemberSuccessCode.TERM_POST_OK, null);
    }

    @PatchMapping("/me/profile")
    public ApiResponse<Void> updateProfile(
            @RequestBody MemberReqDto.Profile dto
    ) {
        // 서비스 로직
        return ApiResponse.onSuccess(MemberSuccessCode.PROFILE_PATCH_OK, null);
    }

    @PatchMapping("/me/nickname")
    public ApiResponse<Void> updateNickname(
            @RequestBody MemberReqDto.Nickname dto
    ) {
        // 서비스 로직
        return ApiResponse.onSuccess(MemberSuccessCode.NICKNAME_PATCH_OK, null);
    }
}
