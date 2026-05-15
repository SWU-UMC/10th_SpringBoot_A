package com.example.umc10th.domain.member.converter;

import com.example.umc10th.domain.member.dto.MemberResDto;
import com.example.umc10th.domain.member.entity.Food;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.entity.mapping.MemberFood;

public class MemberConverter {

    public static MemberResDto.Profile toProfile(
            Member member
    ) {
        return MemberResDto.Profile.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .birth(member.getBirth())
                .address(member.getAddress())
                .fullAddress(member.getFullAddress())
                .gender(member.getGender())
                .food(member.getMemberFoodList().stream().map(MemberFood::getFood).toList())
                .build();
    }

    public static MemberFood toMemberFood(
            Food food,
            Member member
    ) {
        return MemberFood.builder()
                .member(member)
                .food(food)
                .build();
    }
}
