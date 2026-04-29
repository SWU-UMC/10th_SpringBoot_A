package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.MissionResponseDto;
import com.example.umc10th.domain.mission.entity.enums.MissionStatus;
import com.example.umc10th.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc10th.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/missions")
public class MissionController {

    @GetMapping
    public ApiResponse<MissionResponseDto.MissionListResultDto> getMissionList(
            @RequestParam MissionStatus status,
            @RequestParam(required = false) Long regionId
    ) {
        MissionResponseDto.MissionListResultDto result = MissionResponseDto.MissionListResultDto.builder()
                .missionList(List.of(
                        MissionResponseDto.MissionPreviewDto.builder()
                                .memberMissionId(105L)
                                .storeName("가게이름a")
                                .missionCondition("12,000원 이상의 식사")
                                .rewardPoint(500)
                                .completedAt(LocalDate.of(2026, 3, 25))
                                .canWriteReview(true)
                                .build()
                ))
                .hasNext(false)
                .lastId(105L)
                .build();

        return ApiResponse.onSuccess(MissionSuccessCode.MISSION_LIST_READ, result);
    }

    @GetMapping("/summary")
    public ApiResponse<MissionResponseDto.MissionSummaryResultDto> getMissionSummary(
            @RequestParam Long regionId
    ) {
        MissionResponseDto.MissionSummaryResultDto result = MissionResponseDto.MissionSummaryResultDto.builder()
                .regionName("안암동")
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
