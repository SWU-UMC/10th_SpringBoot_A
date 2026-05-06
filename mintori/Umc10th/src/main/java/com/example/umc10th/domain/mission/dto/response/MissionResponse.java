package com.example.umc10th.domain.mission.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record MissionResponse(
        Long missionId,
        Long storeId,
        Integer reward,
        LocalDate deadline,
        String missionSpec,
        LocalDateTime createdAt
) {
}
