package com.example.umc10th.domain.home.converter;

import com.example.umc10th.domain.home.dto.response.HomeResponse;
import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.dto.response.MissionResponse;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.store.entity.Region;
import com.example.umc10th.domain.user.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public class HomeConverter {

    private HomeConverter() {
    }

    public static HomeResponse toHomeResponse(User user, Region region, Page<Mission> missions) {
        List<MissionResponse> missionResponses = missions.getContent().stream()
                .map(MissionConverter::toMissionResponse)
                .toList();

        return new HomeResponse(
                user.getId(),
                user.getName(),
                user.getPoint(),
                region.getId(),
                region.getName(),
                missionResponses,
                missions.getNumber(),
                missions.getSize(),
                missions.getTotalElements(),
                missions.getTotalPages(),
                missions.hasNext()
        );
    }
}
