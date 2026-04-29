package com.example.umc10th.domain.member.dto;

import com.example.umc10th.domain.member.enums.Gender;
import java.time.LocalDate;

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
}