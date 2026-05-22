package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.dto.MemberReqDto;
import com.example.umc10th.domain.member.dto.MemberResDto;
import com.example.umc10th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc10th.domain.member.service.MemberService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.security.entity.AuthMember;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/members")
@Tag(name = "Member", description = "회원 관련 API")
public class MemberController implements MemberControllerDocs{

    private final MemberService memberService;

    @PostMapping("/me/term")
    public ApiResponse<Void> saveTermAgreement(
            @RequestBody MemberReqDto.TermList dto
    ) {
        // 서비스 로직
        return ApiResponse.onSuccess(MemberSuccessCode.TERM_POST_OK, null);
    }

    @GetMapping("/me/profile")
    public ApiResponse<MemberResDto.Profile> getMyProfile(
            @AuthenticationPrincipal AuthMember member
    ) {
        MemberResDto.Profile response = memberService.getMyProfile(member.getMember());
        return ApiResponse.onSuccess(MemberSuccessCode.PROFILE_GET_OK, response);
    }

    @GetMapping("/{id}/profile")
    public ApiResponse<MemberResDto.Profile> getProfile(
            @PathVariable Long id
    ) {
        MemberResDto.Profile response = memberService.getProfile(id);
        return ApiResponse.onSuccess(MemberSuccessCode.PROFILE_GET_OK, response);
    }

    @PatchMapping("/me/profile")
    public ApiResponse<Void> updateProfile(
            @RequestBody @Valid MemberReqDto.Profile dto
    ) {
        memberService.updateProfile(dto);
        return ApiResponse.onSuccess(MemberSuccessCode.PROFILE_PATCH_OK, null);
    }

    @PatchMapping("/me/nickname")
    public ApiResponse<Void> updateNickname(
            @RequestBody @Valid MemberReqDto.Nickname dto
    ) {
        memberService.updateNickname(dto);
        return ApiResponse.onSuccess(MemberSuccessCode.NICKNAME_PATCH_OK, null);
    }
}
