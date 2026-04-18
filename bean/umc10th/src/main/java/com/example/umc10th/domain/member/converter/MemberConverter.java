package com.example.umc10th.domain.member.converter;

import com.example.umc10th.domain.member.dto.MemberReqDto;
import com.example.umc10th.domain.member.dto.MemberResDto;
import com.example.umc10th.domain.member.entity.Member;

public final class MemberConverter {

    private MemberConverter() {
    }

    public static Member toEntity(MemberReqDto.Create request) {
        return Member.builder()
                .name(request.name())
                .gender(request.gender())
                .birthDate(request.birthDate())
                .address(request.address())
                .nickname(request.nickname())
                .build();
    }

    public static MemberResDto.MemberInfo toMemberInfo(Member member) {
        return new MemberResDto.MemberInfo(
                member.getId(),
                member.getName(),
                member.getGender(),
                member.getBirthDate(),
                member.getAddress(),
                member.getNickname()
        );
    }
}