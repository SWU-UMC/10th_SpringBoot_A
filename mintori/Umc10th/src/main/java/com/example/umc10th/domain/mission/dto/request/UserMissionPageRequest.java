package com.example.umc10th.domain.mission.dto.request;

import com.example.umc10th.domain.mission.enums.MissionStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record UserMissionPageRequest(
        @NotNull(message = "유저 ID를 입력해주세요")
        @Positive(message = "유저 ID는 양수여야 합니다")
        Long userId,

        @NotNull(message = "미션 상태를 입력해주세요 (CHALLENGING / COMPLETED)")
        MissionStatus status,

        @NotNull(message = "page 값을 입력해주세요")
        @Min(value = 0, message = "page 는 0 이상이어야 합니다")
        Integer page,

        @NotNull(message = "size 값을 입력해주세요")
        @Min(value = 1, message = "size 는 1 이상이어야 합니다")
        Integer size
) {
}
