package com.example.umc10th.domain.mission.dto.request;

import jakarta.validation.constraints.NotNull;

public record ChallengeMissionRequest(
        @NotNull(message = "유저 ID를 입력해주세요")
        Long userId,

        @NotNull(message = "미션 ID를 입력해주세요")
        Long missionId
) {
}
