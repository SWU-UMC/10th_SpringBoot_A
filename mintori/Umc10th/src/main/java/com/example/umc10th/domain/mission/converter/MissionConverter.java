package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.mission.dto.request.MissionRequest;
import com.example.umc10th.domain.mission.dto.response.MissionResponse;
import com.example.umc10th.domain.mission.dto.response.UserMissionPageResponse;
import com.example.umc10th.domain.mission.dto.response.UserMissionResponse;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.UserMission;
import com.example.umc10th.domain.mission.enums.MissionStatus;
import com.example.umc10th.domain.store.entity.Store;
import com.example.umc10th.domain.user.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public class MissionConverter {

    private MissionConverter() {
    }

    public static Mission toMission(MissionRequest request, Store store) {
        return Mission.builder()
                .store(store)
                .reward(request.reward())
                .deadline(request.deadline())
                .missionSpec(request.missionSpec())
                .build();
    }

    public static MissionResponse toMissionResponse(Mission mission) {
        return new MissionResponse(
                mission.getId(),
                mission.getStore().getId(),
                mission.getReward(),
                mission.getDeadline(),
                mission.getMissionSpec(),
                mission.getCreatedAt()
        );
    }

    public static UserMission toUserMission(User user, Mission mission) {
        return UserMission.builder()
                .user(user)
                .mission(mission)
                .status(MissionStatus.CHALLENGING)
                .build();
    }

    public static UserMissionResponse toUserMissionResponse(UserMission userMission) {
        Mission mission = userMission.getMission();
        Store store = mission.getStore();
        return new UserMissionResponse(
                userMission.getId(),
                userMission.getUser().getId(),
                mission.getId(),
                store.getId(),
                store.getName(),
                mission.getReward(),
                mission.getDeadline(),
                mission.getMissionSpec(),
                userMission.getStatus(),
                userMission.getCreatedAt()
        );
    }

    public static UserMissionPageResponse toUserMissionPageResponse(Page<UserMission> page) {
        List<UserMissionResponse> userMissions = page.getContent().stream()
                .map(MissionConverter::toUserMissionResponse)
                .toList();
        return new UserMissionPageResponse(
                userMissions,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.hasNext()
        );
    }
}
