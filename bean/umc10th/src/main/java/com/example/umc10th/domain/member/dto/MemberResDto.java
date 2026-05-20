package com.example.umc10th.domain.member.dto;

import com.example.umc10th.domain.member.enums.Gender;
import java.time.LocalDate;
import java.util.List;

public class MemberResDto {

    public record MemberInfo(
            Long memberId,
            String name,
            Gender gender,
            LocalDate birthDate,
            String address,
            String nickname
    ) {
    }

    public record GetMyPageResponse(
            String name,
            String profileUrl,
            String email,
            String phoneNumber,
            Integer point
    ) {
    }

    public record SignUpResponse(
            Long memberId,
            String email,
            String nickname,
            List<Long> preferCategoryIds
    ) {
    }
}
