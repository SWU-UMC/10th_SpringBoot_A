package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.request.ChallengeMissionRequest;
import com.example.umc10th.domain.mission.dto.request.MissionRequest;
import com.example.umc10th.domain.mission.dto.request.UserMissionPageRequest;
import com.example.umc10th.domain.mission.dto.response.MissionResponse;
import com.example.umc10th.domain.mission.dto.response.UserMissionPageResponse;
import com.example.umc10th.domain.mission.dto.response.UserMissionResponse;
import com.example.umc10th.domain.mission.service.MissionService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.GeneralSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/missions")
public class MissionController {

    private final MissionService missionService;

    @PostMapping
    public ApiResponse<MissionResponse> addMission(@Valid @RequestBody MissionRequest request) {
        return ApiResponse.onSuccess(GeneralSuccessCode.CREATED, missionService.addMission(request));
    }

    @PostMapping("/challenge")
    public ApiResponse<UserMissionResponse> challengeMission(
            @Valid @RequestBody ChallengeMissionRequest request) {
        return ApiResponse.onSuccess(GeneralSuccessCode.CREATED, missionService.challengeMission(request));
    }

    @PostMapping("/me")
    public ApiResponse<UserMissionPageResponse> getMyMissions(
            @Valid @RequestBody UserMissionPageRequest request
    ) {
        return ApiResponse.onSuccess(
                GeneralSuccessCode.OK,
                missionService.getUserMissions(request)
        );
    }

    @PatchMapping("/user-missions/{userMissionId}/complete")
    public ApiResponse<UserMissionResponse> completeMission(@PathVariable Long userMissionId) {
        return ApiResponse.onSuccess(
                GeneralSuccessCode.OK,
                missionService.completeMission(userMissionId)
        );
    }
}
