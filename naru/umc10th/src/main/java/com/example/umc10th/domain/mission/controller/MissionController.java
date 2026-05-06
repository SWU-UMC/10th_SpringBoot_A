package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.MissionResponseDto;
import com.example.umc10th.domain.mission.entity.enums.MissionStatus;
import com.example.umc10th.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc10th.domain.mission.service.MissionService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.common.dto.CursorPageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/missions")
public class MissionController {

    private final MissionService missionService;

    @GetMapping
    public ApiResponse<CursorPageResponseDto<MissionResponseDto.MissionPreviewDto>> getMissionList(
            @RequestParam Long userId,
            @RequestParam(required = false) MissionStatus status,
            @RequestParam(required = false) Long regionId,
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "10") int size
    ) {
        CursorPageResponseDto<MissionResponseDto.MissionPreviewDto> result =
                missionService.getMyMissions(userId, status, regionId, cursor, size);

        return ApiResponse.onSuccess(MissionSuccessCode.MISSION_LIST_READ, result);
    }

    @GetMapping("/summary")
    public ApiResponse<MissionResponseDto.MissionSummaryResultDto> getMissionSummary(
            @RequestParam Long regionId
    ) {
        MissionResponseDto.MissionSummaryResultDto result = MissionResponseDto.MissionSummaryResultDto.builder()
                .regionName("압구정")
                .successCount(7)
                .totalCount(10)
                .build();

        return ApiResponse.onSuccess(MissionSuccessCode.MISSION_SUCCESS_SUMMARY_READ, result);
    }

    @PatchMapping("/{memberMissionId}/success")
    public ApiResponse<MissionResponseDto.MissionSuccessResultDto> requestMissionSuccess(
            @PathVariable Long memberMissionId
    ) {
        MissionResponseDto.MissionSuccessResultDto result = MissionResponseDto.MissionSuccessResultDto.builder()
                .code("123445")
                .build();

        return ApiResponse.onSuccess(MissionSuccessCode.MISSION_SUCCESS_REQUEST, result);
    }
}
