package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.MissionReqDto;
import com.example.umc10th.domain.mission.dto.MissionResDto;
import com.example.umc10th.domain.mission.enums.MemberMissionStatus;
import com.example.umc10th.domain.mission.service.MissionService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/missions")
@RequiredArgsConstructor
@Validated
public class MissionController {

    private final MissionService missionService;

    @GetMapping("/{missionId}")
    public ResponseEntity<ApiResponse<MissionResDto.MissionInfo>> getMission(@PathVariable Long missionId) {
        return ResponseEntity.ok(ApiResponse.onSuccess(missionService.getMission(missionId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MissionResDto.MissionInfo>> createMission(@Valid @RequestBody MissionReqDto.Create request) {
        return ResponseEntity.ok(ApiResponse.onSuccess(missionService.createMission(request)));
    }

    @PostMapping("/my/challenging")
    public ResponseEntity<ApiResponse<MissionResDto.MemberMissionPageResponse>> getMyChallengingMissions(
            @Valid @RequestBody MissionReqDto.MyChallengingMissionsRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.onSuccess(missionService.getMyChallengingMissions(request)));
    }

    @GetMapping("/members/{memberId}")
    public ResponseEntity<ApiResponse<MissionResDto.MemberMissionPageResponse>> getMemberMissions(
            @PathVariable Long memberId,
            @RequestParam MemberMissionStatus status,
            @RequestParam(defaultValue = "1") @Min(1) Integer page
    ) {
        return ResponseEntity.ok(ApiResponse.onSuccess(missionService.getMemberMissions(memberId, status, page)));
    }

    @GetMapping("/home")
    public ResponseEntity<ApiResponse<MissionResDto.HomeMissionPageResponse>> getHomeMissions(
            @RequestParam @NotNull Long regionId,
            @RequestParam(required = false) Long memberId,
            @RequestParam(defaultValue = "1") @Min(1) Integer page
    ) {
        return ResponseEntity.ok(ApiResponse.onSuccess(missionService.getHomeMissions(regionId, memberId, page)));
    }
}
