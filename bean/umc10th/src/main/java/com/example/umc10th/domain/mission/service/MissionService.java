package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.mission.dto.MissionReqDto;
import com.example.umc10th.domain.mission.dto.MissionResDto;

public interface MissionService {
    MissionResDto.MissionInfo getMission(Long missionId);

    MissionResDto.MissionInfo createMission(MissionReqDto.Create request);
}