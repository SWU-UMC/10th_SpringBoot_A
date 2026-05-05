package com.example.umc10th.domain.mission.dto.response;

import com.example.umc10th.domain.mission.enums.MissionStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserMissionResponse(
        Long userMissionId,
        Long userId,
        Long missionId,
        Long storeId,
        String storeName,
        Integer reward,
        LocalDate deadline,
        String missionSpec,
        MissionStatus status,
        LocalDateTime createdAt
) {
}
