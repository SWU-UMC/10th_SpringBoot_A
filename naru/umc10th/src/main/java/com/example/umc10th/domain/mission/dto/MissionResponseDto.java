package com.example.umc10th.domain.mission.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

public class MissionResponseDto {

    @Builder
    public record MissionListResultDto(
            List<MissionPreviewDto> missionList,
            Boolean hasNext,
            Long lastId
    ) {
    }

    @Builder
    public record MissionPreviewDto(
            Long memberMissionId,
            String storeName,
            String missionCondition,
            Integer rewardPoint,
            LocalDate completedAt,
            Boolean canWriteReview
    ) {
    }

    @Builder
    public record MissionSummaryResultDto(
            String regionName,
            Integer successCount,
            Integer totalCount
    ) {
    }

    @Builder
    public record MissionSuccessResultDto(
            String code
    ) {
    }
}
