package com.example.umc10th.domain.auth.converter;

import com.example.umc10th.domain.auth.dto.AuthReqDto;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.enums.SocialType;

import java.util.UUID;


public class AuthConverter {

    public static Member toMember(
        AuthReqDto.Signup dto,
        String salt
    ) {
        return Member.builder()
                .email(dto.email())
                .password(salt)
                .nickname(dto.nickname())
                .gender(dto.gender())
                .birth(dto.birth())
                .address(dto.address())
                .fullAddress(dto.fullAddress())
                .socialId("email"+UUID.randomUUID())
                .socialType(SocialType.EMAIL)
                .build();
    }
}
