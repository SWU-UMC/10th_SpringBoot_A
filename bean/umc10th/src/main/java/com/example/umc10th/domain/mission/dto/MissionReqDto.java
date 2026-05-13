package com.example.umc10th.domain.mission.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class MissionReqDto {

    public record Create(
            @NotNull Long storeId,
            @NotBlank String content,
            @Min(0) int rewardPoint
    ) {
    }

    public record MyChallengingMissionsRequest(
            @NotNull @Positive Long memberId,
            @NotNull @Min(1) Integer page,
            @NotNull @Min(1) Integer size
    ) {
    }
}
