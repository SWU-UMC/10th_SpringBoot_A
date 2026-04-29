package com.example.umc10th.domain.mission.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MissionReqDto {

    public record Create(
            @NotNull Long storeId,
            @NotBlank String content,
            @Min(0) int rewardPoint
    ) {
    }
}