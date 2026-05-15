package com.example.umc10th.domain.member.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberSuccessCode implements BaseSuccessCode {

    TERM_POST_OK(HttpStatus.OK,
            "TERM200_1",
            "약관이 저장되었습니다."),

    PROFILE_PATCH_OK(HttpStatus.OK,
            "MEMBER200_1",
            "프로필 정보가 저장되었습니다."),

    NICKNAME_PATCH_OK(HttpStatus.OK,
            "MEMBER200_2",
            "닉네임이 수정되었습니다."),

    PROFILE_GET_OK(HttpStatus.OK,
            "MEMBER200_3",
            "프로필 정보가 조회되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
