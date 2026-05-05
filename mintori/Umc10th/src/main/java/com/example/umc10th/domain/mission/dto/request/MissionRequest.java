package com.example.umc10th.domain.mission.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record MissionRequest(
        @NotNull(message = "가게 ID를 입력해주세요")
        Long storeId,

        @NotNull(message = "리워드를 입력해주세요")
        @Min(value = 0, message = "리워드는 0 이상이어야 합니다")
        Integer reward,

        LocalDate deadline,

        @NotBlank(message = "미션 내용을 입력해주세요")
        @Size(max = 500)
        String missionSpec
) {
}
