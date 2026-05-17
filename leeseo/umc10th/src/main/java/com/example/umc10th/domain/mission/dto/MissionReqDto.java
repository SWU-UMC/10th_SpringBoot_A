package com.example.umc10th.domain.mission.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class MissionReqDto {

    @Schema(name = "MemberID", description = "회원 아이디를 입력합니다.")
    public record MemberId(
            @NotNull
            Long id
    ){}
}
