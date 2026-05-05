package com.example.umc10th.domain.mission.dto.response;

import java.util.List;

public record UserMissionPageResponse(
        List<UserMissionResponse> userMissions,
        Integer page,
        Integer size,
        Long totalElements,
        Integer totalPages,
        Boolean hasNext
) {
}
