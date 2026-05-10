package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.mission.dto.MissionReqDto;
import com.example.umc10th.domain.mission.dto.MissionResDto;
import com.example.umc10th.domain.mission.entity.MemberMission;
import com.example.umc10th.domain.mission.entity.Mission;
import org.springframework.data.domain.Page;
import com.example.umc10th.domain.store.entity.Store;
import java.util.List;

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

    public static MissionResDto.MemberMissionPageItem toMemberMissionPageItem(MemberMission memberMission) {
        Mission mission = memberMission.getMission();

        return new MissionResDto.MemberMissionPageItem(
                memberMission.getId(),
                mission.getId(),
                mission.getStore().getName(),
                mission.getContent(),
                mission.getRewardPoint(),
                memberMission.getStatus()
        );
    }

    public static MissionResDto.HomeMissionPageItem toHomeMissionPageItem(Mission mission) {
        return new MissionResDto.HomeMissionPageItem(
                mission.getId(),
                mission.getStore().getName(),
                mission.getContent(),
                mission.getRewardPoint(),
                mission.getStore().getScore()
        );
    }

    public static MissionResDto.MemberMissionPageResponse toMemberMissionPageResponse(Page<MemberMission> page) {
        List<MissionResDto.MemberMissionPageItem> missions = page.getContent()
                .stream()
                .map(MissionConverter::toMemberMissionPageItem)
                .toList();

        return new MissionResDto.MemberMissionPageResponse(
                missions,
                missions.size(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.isFirst(),
                page.isLast()
        );
    }

    public static MissionResDto.HomeMissionPageResponse toHomeMissionPageResponse(Page<Mission> page) {
        List<MissionResDto.HomeMissionPageItem> missions = page.getContent()
                .stream()
                .map(MissionConverter::toHomeMissionPageItem)
                .toList();

        return new MissionResDto.HomeMissionPageResponse(
                missions,
                missions.size(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.isFirst(),
                page.isLast()
        );
    }
}
