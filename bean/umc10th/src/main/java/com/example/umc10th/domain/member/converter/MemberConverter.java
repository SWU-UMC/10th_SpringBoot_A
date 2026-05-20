package com.example.umc10th.domain.member.converter;

import com.example.umc10th.domain.member.dto.MemberReqDto;
import com.example.umc10th.domain.member.dto.MemberResDto;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.store.entity.FoodCategory;
import java.util.List;

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

    public static Member toEntity(MemberReqDto.SignUpRequest request, String encodedPassword) {
        return Member.builder()
                .name(request.name())
                .gender(request.gender())
                .birthDate(request.birthDate())
                .address(request.address())
                .nickname(request.nickname())
                .email(request.email())
                .password(encodedPassword)
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

    public static MemberResDto.GetMyPageResponse toMyPageResponse(Member member, Integer point) {
        return new MemberResDto.GetMyPageResponse(
                member.getName(),
                null,
                member.getEmail(),
                null,
                point
        );
    }

    public static MemberResDto.SignUpResponse toSignUpResponse(Member member, List<FoodCategory> categories) {
        List<Long> preferCategoryIds = categories.stream()
                .map(FoodCategory::getId)
                .toList();

        return new MemberResDto.SignUpResponse(
                member.getId(),
                member.getEmail(),
                member.getNickname(),
                preferCategoryIds
        );
    }
}
