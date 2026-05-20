package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.mission.dto.request.ChallengeMissionRequest;
import com.example.umc10th.domain.mission.dto.request.MissionRequest;
import com.example.umc10th.domain.mission.dto.request.UserMissionPageRequest;
import com.example.umc10th.domain.mission.dto.response.MissionResponse;
import com.example.umc10th.domain.mission.dto.response.UserMissionPageResponse;
import com.example.umc10th.domain.mission.dto.response.UserMissionResponse;

public interface MissionService {

    MissionResponse addMission(MissionRequest request);

    UserMissionResponse challengeMission(ChallengeMissionRequest request);

    UserMissionPageResponse getUserMissions(UserMissionPageRequest request);

    UserMissionResponse completeMission(Long userMissionId);
}
