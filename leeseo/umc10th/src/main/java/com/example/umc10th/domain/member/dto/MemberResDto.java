package com.example.umc10th.domain.member.dto;

import com.example.umc10th.domain.member.entity.Food;
import com.example.umc10th.domain.member.enums.Gender;
import com.example.umc10th.domain.mission.enums.Address;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

public class MemberResDto {

    @Builder
    public record Profile (
            Long id,
            String nickname,
            Gender gender,
            LocalDate birth,
            Address address,
            String fullAddress,
            List<Food> food
    ) {}
}
