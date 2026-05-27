package com.example.umc10th.domain.mission.dto;

import com.example.umc10th.domain.mission.enums.MemberMissionStatus;
import java.util.List;

public class MissionResDto {

    public record MissionInfo(
            Long missionId,
            Long storeId,
            String content,
            int rewardPoint
    ) {
    }

    public record MemberMissionPageItem(
            Long memberMissionId,
            Long missionId,
            String storeName,
            String missionContent,
            Integer rewardPoint,
            MemberMissionStatus status
    ) {
    }

    public record HomeMissionPageItem(
            Long missionId,
            String storeName,
            String missionContent,
            Integer rewardPoint,
            Float storeScore
    ) {
    }

    public record MissionPageResponse<T>(
            List<T> missions,
            Integer listSize,
            Boolean isFirst,
            Boolean isLast,
            Boolean hasNext
    ) {
    }
}
