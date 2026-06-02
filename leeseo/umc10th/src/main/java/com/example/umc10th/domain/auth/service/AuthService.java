package com.example.umc10th.domain.auth.service;

import com.example.umc10th.domain.auth.converter.AuthConverter;
import com.example.umc10th.domain.auth.dto.AuthReqDto;
import com.example.umc10th.domain.auth.dto.AuthResDto;
import com.example.umc10th.domain.auth.exception.AuthException;
import com.example.umc10th.domain.auth.exception.code.AuthErrorCode;
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
import com.example.umc10th.global.security.entity.AuthMember;
import com.example.umc10th.global.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final JwtUtil jwtUtil;

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

    public AuthResDto.AccessToken login(AuthReqDto.Login dto) {
        Member member = memberRepository.findByEmail(dto.email())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        if (!passwordEncoder.matches(dto.password(), member.getPassword())) {
            throw new AuthException(AuthErrorCode.INVALID_PASSWORD);
        }

        AuthMember authMember = new AuthMember(member);
        String accessToken = jwtUtil.createAccessToken(authMember);

        return AuthConverter.toAccessToken(accessToken);
    }
}
