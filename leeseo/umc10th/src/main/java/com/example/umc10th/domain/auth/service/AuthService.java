package com.example.umc10th.domain.auth.service;

import com.example.umc10th.domain.auth.converter.AuthConverter;
import com.example.umc10th.domain.auth.dto.AuthReqDto;
import com.example.umc10th.domain.auth.dto.AuthResDto;
import com.example.umc10th.domain.member.converter.MemberConverter;
import com.example.umc10th.domain.member.entity.Food;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.enums.FoodType;
import com.example.umc10th.domain.member.exception.FoodException;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.FoodErrorCode;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.FoodRepository;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final FoodRepository foodRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Transactional
    public AuthResDto.Id signUp(AuthReqDto.Signup dto) {
        String salt = passwordEncoder.encode(dto.password());
        Member member = AuthConverter.toMember(dto, salt);
        List<Food> list = new ArrayList<>();
        if (dto.food() != null) {
            for (FoodType f : dto.food()) {
                Food food = foodRepository.findByName(f)
                        .orElseThrow(() -> new FoodException(FoodErrorCode.FOOD_TYPE_NOT_FOUND));
                list.add(food);
            }
        }
        memberRepository.save(member);
        member.updateFoodList(list);
        return AuthResDto.Id.builder().id(member.getId()).build();
    }
}
