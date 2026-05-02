package com.example.umc10th.domain.mission.dto;

import com.example.umc10th.domain.member.enums.FoodType;

import java.time.LocalDate;
import java.util.List;

public class MissionResDto {

    public record Achievement(
       Integer totalCount,
       Integer successCount
    ) {}

    public record StoreList(
        List<StoreInfo> stores
    ) {}

    public record StoreInfo(
        String storeName,
        FoodType storeType,
        LocalDate deadline,
        Integer minCost,
        Integer point
    ) {}

    public record OwnerNumber(
       Integer ownerNumber
    ) {}
}
