package com.example.umc10th.domain.home.dto.response;

import com.example.umc10th.domain.mission.dto.response.MissionResponse;

import java.util.List;

public record HomeResponse(
        Long userId,
        String userName,
        Long point,
        Long regionId,
        String regionName,
        List<MissionResponse> challengeableMissions,
        Integer page,
        Integer size,
        Long totalElements,
        Integer totalPages,
        Boolean hasNext
) {
}
