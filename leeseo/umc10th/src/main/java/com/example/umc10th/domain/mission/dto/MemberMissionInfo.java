package com.example.umc10th.domain.mission.dto;

import com.example.umc10th.domain.member.enums.FoodType;
import com.example.umc10th.domain.mission.enums.Status;

import java.time.LocalDate;

public record MemberMissionInfo(
        Long missionId,
        Long memberMissionId,
        String storeName,
        FoodType storeType,
        LocalDate deadline,
        Integer minCost,
        Integer point,
        Status status
) {}
