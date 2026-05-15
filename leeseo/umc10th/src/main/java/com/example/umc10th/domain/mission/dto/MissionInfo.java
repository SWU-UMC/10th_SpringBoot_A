package com.example.umc10th.domain.mission.dto;

import com.example.umc10th.domain.member.enums.FoodType;

import java.time.LocalDate;

public record MissionInfo(
        Long missionId,
        String storeName,
        FoodType storeType,
        LocalDate deadline,
        Integer minCost,
        Integer point
) {}
