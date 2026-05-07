package com.example.umc10th.domain.member.dto;

import com.example.umc10th.domain.member.entity.Food;
import com.example.umc10th.domain.member.enums.Gender;
import com.example.umc10th.domain.mission.enums.Address;

import java.time.LocalDate;
import java.util.List;

public class MemberReqDto {

    public record TermList (
        List<Term> terms
    ) {}

    public record Term (
        Long termId,
        Boolean agreed
    ) {}

    public record Profile (
        String nickname,
        Gender gender,
        LocalDate birth,
        Address address,
        String fullAddress,
        List<Food> food
    ) {}

    public record Nickname (
       String nickname
    ) {}
}
