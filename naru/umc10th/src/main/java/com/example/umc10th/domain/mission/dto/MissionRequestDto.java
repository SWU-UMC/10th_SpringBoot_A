package com.example.umc10th.domain.mission.dto;

import com.example.umc10th.domain.mission.entity.enums.MissionStatus;

public class MissionRequestDto {

    public record MissionListRequestDto(
            MissionStatus status,
            Long regionId
    ) {
    }

    public record MissionSummaryRequestDto(
            Long regionId
    ) {
    }
}
