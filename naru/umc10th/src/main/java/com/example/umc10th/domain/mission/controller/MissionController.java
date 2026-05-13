package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.MissionResponseDto;
import com.example.umc10th.domain.mission.entity.enums.MissionStatus;
import com.example.umc10th.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc10th.domain.mission.service.MissionService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.common.dto.CursorPageResponseDto;
import com.example.umc10th.global.common.dto.OffsetPageResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "미션 API", description = "미션 조회 및 성공 요청 API")
public class MissionController {

    private final MissionService missionService;

    @Operation(summary = "내 미션 목록 조회 API")
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

    @Operation(summary = "내 미션 목록 오프셋 페이징 조회 API")
    @GetMapping("/offset")
    public ApiResponse<OffsetPageResponseDto<MissionResponseDto.MissionPreviewDto>> getMissionListByOffset(
            @RequestParam Long userId,
            @RequestParam(required = false) MissionStatus status,
            @RequestParam(required = false) Long regionId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        OffsetPageResponseDto<MissionResponseDto.MissionPreviewDto> result =
                missionService.getMyMissionsByOffset(userId, status, regionId, page, size);

        return ApiResponse.onSuccess(MissionSuccessCode.MISSION_LIST_READ, result);
    }

    @Operation(summary = "도전 가능한 미션 목록 조회 API")
    @GetMapping("/available")
    public ApiResponse<CursorPageResponseDto<MissionResponseDto.AvailableMissionDto>> getAvailableMissionList(
            @RequestParam Long userId,
            @RequestParam Long regionId,
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "10") int size
    ) {
        CursorPageResponseDto<MissionResponseDto.AvailableMissionDto> result =
                missionService.getAvailableMissions(userId, regionId, cursor, size);

        return ApiResponse.onSuccess(MissionSuccessCode.MISSION_LIST_READ, result);
    }

    @Operation(summary = "지역별 미션 진행 요약 조회 API")
    @GetMapping("/summary")
    public ApiResponse<MissionResponseDto.MissionSummaryResultDto> getMissionSummary(
            @RequestParam Long userId,
            @RequestParam Long regionId
    ) {
        MissionResponseDto.MissionSummaryResultDto result =
                missionService.getRegionProgress(userId, regionId);

        return ApiResponse.onSuccess(MissionSuccessCode.MISSION_SUCCESS_SUMMARY_READ, result);
    }

    @Operation(summary = "미션 성공 인증 요청 API")
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
