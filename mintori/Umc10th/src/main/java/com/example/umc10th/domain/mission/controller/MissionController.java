package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.request.ChallengeMissionRequest;
import com.example.umc10th.domain.mission.dto.request.MissionRequest;
import com.example.umc10th.domain.mission.dto.response.MissionResponse;
import com.example.umc10th.domain.mission.dto.response.UserMissionPageResponse;
import com.example.umc10th.domain.mission.dto.response.UserMissionResponse;
import com.example.umc10th.domain.mission.service.MissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/missions")
public class MissionController {

    private final MissionService missionService;

    @PostMapping
    public ResponseEntity<MissionResponse> addMission(@Valid @RequestBody MissionRequest request) {
        return ResponseEntity.ok(missionService.addMission(request));
    }

    @PostMapping("/challenge")
    public ResponseEntity<UserMissionResponse> challengeMission(
            @Valid @RequestBody ChallengeMissionRequest request) {
        return ResponseEntity.ok(missionService.challengeMission(request));
    }

    @GetMapping("/challenging")
    public ResponseEntity<UserMissionPageResponse> getChallengingMissions(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(missionService.getChallengingMissions(userId, page, size));
    }

    @PatchMapping("/user-missions/{userMissionId}/complete")
    public ResponseEntity<UserMissionResponse> completeMission(@PathVariable Long userMissionId) {
        return ResponseEntity.ok(missionService.completeMission(userMissionId));
    }
}
