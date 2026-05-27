package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.dto.MemberReqDto;
import com.example.umc10th.domain.member.dto.MemberResDto;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc10th.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class MemberController {

    @PostMapping("/me")
    public ResponseEntity<ApiResponse<MemberResDto.GetMyPageResponse>> getMyPage(
            @Valid @RequestBody MemberReqDto.GetMyPageRequest request
    ) {
        if (request.id() <= 0) {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
        }

        MemberResDto.GetMyPageResponse result = new MemberResDto.GetMyPageResponse(
                "bean",
                "https://example.com/profile/bean.png",
                "bean@umc.com",
                null,
                2500
        );

        return ResponseEntity.ok(ApiResponse.of(MemberSuccessCode.GET_MY_PAGE_SUCCESS, result));
    }
}