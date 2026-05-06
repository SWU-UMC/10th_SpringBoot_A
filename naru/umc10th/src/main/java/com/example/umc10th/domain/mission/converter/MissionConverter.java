package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.mission.dto.MissionResponseDto;
import com.example.umc10th.domain.mission.entity.mapping.UserMission;

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
}
