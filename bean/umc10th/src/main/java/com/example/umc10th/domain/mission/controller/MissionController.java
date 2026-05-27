package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.MissionReqDto;
import com.example.umc10th.domain.mission.dto.MissionResDto;
import com.example.umc10th.domain.mission.service.MissionService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/missions")
@RequiredArgsConstructor
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
}
