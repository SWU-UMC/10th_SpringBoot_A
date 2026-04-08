package com.example.umc10th.domain.mission.dto;

public class MissionResDto {

    public record MissionInfo(
            Long missionId,
            Long storeId,
            String content,
            int rewardPoint
    ) {
    }
}