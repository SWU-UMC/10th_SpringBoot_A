package com.example.umc10th.domain.mission.dto;

import com.example.umc10th.domain.mission.entity.enums.MissionStatus;
import lombok.Builder;

import java.time.LocalDate;

public class MissionResponseDto {

    @Builder
    public record MissionPreviewDto(
            Long userMissionId,
            String storeName,
            Integer rewardPoint,
            String missionContent,
            MissionStatus status,
            LocalDate completedAt,
            Boolean canWriteReview
    ) {
    }

    @Builder
    public record AvailableMissionDto(
            Long missionId,
            String storeName,
            Integer rewardPoint,
            String missionContent,
            Long daysUntilDeadline,
            String foodCategory
    ) {
    }

    @Builder
    public record MissionSummaryResultDto(
            String regionName,
            Integer successCount,
            Integer totalCount,
            String progress
    ) {
    }

    @Builder
    public record MissionSuccessResultDto(
            String code
    ) {
    }
}
