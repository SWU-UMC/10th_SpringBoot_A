package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.converter.MemberConverter;
import com.example.umc10th.domain.member.dto.MemberReqDto;
import com.example.umc10th.domain.member.dto.MemberResDto;
import com.example.umc10th.domain.member.entity.Food;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.enums.FoodType;
import com.example.umc10th.domain.member.exception.FoodException;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.FoodErrorCode;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.FoodRepository;
import com.example.umc10th.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final FoodRepository foodRepository;

    public MemberResDto.Profile getProfile(Long id) {
        Member member = memberRepository.findMemberById(id)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        return MemberConverter.toProfile(member);
    }

    @Transactional
    public void updateProfile(MemberReqDto.Profile dto) {
        Member member = memberRepository.findMemberById(dto.id())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        if (dto.nickname() != null) {
            member.updateNickname(dto.nickname());
        }

        if (dto.gender() != null) {
            member.updateGender(dto.gender());
        }

        if (dto.birth() != null) {
            member.updateBirth(dto.birth());
        }

        if (dto.address() != null) {
            member.updateAddress(dto.address());
        }

        if (dto.fullAddress() != null) {
            member.updateFullAddress(dto.fullAddress());
        }

        if (dto.food() != null) {
            List<Food> list = new ArrayList<>();
            for (FoodType f : dto.food()) {
                Food food = foodRepository.findByName(f)
                                .orElseThrow(() -> new FoodException(FoodErrorCode.FOOD_TYPE_NOT_FOUND));
                list.add(food);
            }

            member.updateFoodList(list);
        }
    }

    @Transactional
    public void updateNickname(MemberReqDto.Nickname dto) {
        Member member = memberRepository.findMemberById(dto.id())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        if (dto.nickname() != null) {
            member.updateNickname(dto.nickname());
        }
    }
}
