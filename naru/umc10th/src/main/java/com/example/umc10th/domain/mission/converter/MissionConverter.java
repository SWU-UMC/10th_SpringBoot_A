package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.mission.dto.MissionResponseDto;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.mapping.UserMission;
import com.example.umc10th.domain.region.entity.RegionProgress;
import com.example.umc10th.domain.store.entity.Region;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MissionConverter {

    public static MissionResponseDto.MissionPreviewDto toMissionPreviewDto(
            UserMission userMission,
            boolean canWriteReview
    ) {
        return MissionResponseDto.MissionPreviewDto.builder()
                .userMissionId(userMission.getId())
                .storeName(userMission.getMission().getStore().getName())
                .rewardPoint(userMission.getMission().getRewardPoint())
                .missionContent(userMission.getMission().getContent())
                .status(userMission.getStatus())
                .completedAt(userMission.getVerifiedAt() == null ? null : userMission.getVerifiedAt().toLocalDate())
                .canWriteReview(canWriteReview)
                .build();
    }

    public static MissionResponseDto.AvailableMissionDto toAvailableMissionDto(Mission mission) {
        return MissionResponseDto.AvailableMissionDto.builder()
                .missionId(mission.getId())
                .storeName(mission.getStore().getName())
                .rewardPoint(mission.getRewardPoint())
                .missionContent(mission.getContent())
                .daysUntilDeadline(calculateDaysUntilDeadline(mission))
                .foodCategory(mission.getStore().getFoodCategory() == null
                        ? null
                        : mission.getStore().getFoodCategory().getName())
                .build();
    }

    private static Long calculateDaysUntilDeadline(Mission mission) {
        if (mission.getEndAt() == null) {
            return null;
        }

        long days = ChronoUnit.DAYS.between(LocalDate.now(), mission.getEndAt().toLocalDate());
        return Math.max(days, 0);
    }

    public static MissionResponseDto.MissionSummaryResultDto toMissionSummaryResultDto(
            Region region,
            RegionProgress regionProgress
    ) {
        int successCount = calculateRegionSuccessCount(regionProgress);
        int totalCount = 10;

        return MissionResponseDto.MissionSummaryResultDto.builder()
                .regionName(region.getName())
                .successCount(successCount)
                .totalCount(totalCount)
                .progress(successCount + "/" + totalCount)
                .build();
    }

    private static int calculateRegionSuccessCount(RegionProgress regionProgress) {
        if (regionProgress == null || regionProgress.getSuccessCount() == null) {
            return 0;
        }

        return Math.min(regionProgress.getSuccessCount(), 10);
    }
}
