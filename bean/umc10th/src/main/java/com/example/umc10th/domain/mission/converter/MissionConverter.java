package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.mission.dto.MissionReqDto;
import com.example.umc10th.domain.mission.dto.MissionResDto;
import com.example.umc10th.domain.mission.entity.MemberMission;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.store.entity.Store;
import java.util.List;
import java.util.function.Function;
import org.springframework.data.domain.Slice;

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

    public static MissionResDto.MissionPageResponse<MissionResDto.MemberMissionPageItem> toMemberMissionPageResponse(
            Slice<MemberMission> slice
    ) {
        return toMissionPageResponse(slice, MissionConverter::toMemberMissionPageItem);
    }

    public static MissionResDto.MissionPageResponse<MissionResDto.HomeMissionPageItem> toHomeMissionPageResponse(
            Slice<Mission> slice
    ) {
        return toMissionPageResponse(slice, MissionConverter::toHomeMissionPageItem);
    }

    private static <T, R> MissionResDto.MissionPageResponse<R> toMissionPageResponse(
            Slice<T> slice,
            Function<T, R> mapper
    ) {
        List<R> missions = slice.getContent()
                .stream()
                .map(mapper)
                .toList();

        return new MissionResDto.MissionPageResponse<>(
                missions,
                missions.size(),
                slice.isFirst(),
                slice.isLast(),
                slice.hasNext()
        );
    }
}
