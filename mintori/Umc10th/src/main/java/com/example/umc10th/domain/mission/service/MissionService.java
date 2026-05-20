package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.mission.dto.request.ChallengeMissionRequest;
import com.example.umc10th.domain.mission.dto.request.MissionRequest;
import com.example.umc10th.domain.mission.dto.response.MissionResponse;
import com.example.umc10th.domain.mission.dto.response.UserMissionPageResponse;
import com.example.umc10th.domain.mission.dto.response.UserMissionResponse;
import com.example.umc10th.domain.mission.enums.MissionStatus;

public interface MissionService {

    /** 특정 가게에 미션 추가 */
    MissionResponse addMission(MissionRequest request);

    /** 미션을 도전 중인 미션 목록에 추가 */
    UserMissionResponse challengeMission(ChallengeMissionRequest request);

    /** 유저의 미션을 상태별로 페이징 조회 (CHALLENGING / COMPLETED) */
    UserMissionPageResponse getUserMissions(Long userId, MissionStatus status, int page, int size);

    /** 도전 중 미션을 완료 상태로 변경 */
    UserMissionResponse completeMission(Long userMissionId);
}
