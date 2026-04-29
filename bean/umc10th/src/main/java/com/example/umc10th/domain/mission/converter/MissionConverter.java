package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.mission.dto.MissionReqDto;
import com.example.umc10th.domain.mission.dto.MissionResDto;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.store.entity.Store;

public final class MissionConverter {

    private MissionConverter() {
    }

    public static Mission toEntity(MissionReqDto.Create request, Store store) {
        return Mission.builder()
                .store(store)
                .content(request.content())
                .rewardPoint(request.rewardPoint())
                .build();
    }

    public static MissionResDto.MissionInfo toMissionInfo(Mission mission) {
        return new MissionResDto.MissionInfo(
                mission.getId(),
                mission.getStore().getId(),
                mission.getContent(),
                mission.getRewardPoint()
        );
    }
}