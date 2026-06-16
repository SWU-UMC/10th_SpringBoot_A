package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.dto.MemberReqDto;
import com.example.umc10th.domain.member.dto.MemberResDto;
import com.example.umc10th.domain.member.entity.Member;

public interface MemberService {
    MemberResDto.MemberInfo getMember(Long memberId);

    MemberResDto.MemberInfo createMember(MemberReqDto.Create request);

    MemberResDto.GetMyPageResponse getMyPage(Long memberId);

    MemberResDto.GetMyPageResponse getMyPage(Member member);

    MemberResDto.SignUpResponse signUp(MemberReqDto.SignUpRequest request);

    MemberResDto.LoginResponse login(MemberReqDto.LoginRequest request);
}
