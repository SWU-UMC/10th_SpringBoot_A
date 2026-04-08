package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.dto.MemberReqDto;
import com.example.umc10th.domain.member.dto.MemberResDto;

public interface MemberService {
    MemberResDto.MemberInfo getMember(Long memberId);

    MemberResDto.MemberInfo createMember(MemberReqDto.Create request);
}