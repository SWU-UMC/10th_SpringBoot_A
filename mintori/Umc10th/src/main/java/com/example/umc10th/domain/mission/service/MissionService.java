package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.mission.dto.request.ChallengeMissionRequest;
import com.example.umc10th.domain.mission.dto.request.MissionRequest;
import com.example.umc10th.domain.mission.dto.response.MissionResponse;
import com.example.umc10th.domain.mission.dto.response.UserMissionPageResponse;
import com.example.umc10th.domain.mission.dto.response.UserMissionResponse;

public interface MissionService {

    /** 특정 가게에 미션 추가 */
    MissionResponse addMission(MissionRequest request);

    /** 미션을 도전 중인 미션 목록에 추가 */
    UserMissionResponse challengeMission(ChallengeMissionRequest request);

    /** 유저의 도전 중인 미션 목록 페이징 조회 */
    UserMissionPageResponse getChallengingMissions(Long userId, int page, int size);

    /** 도전 중 미션을 완료 상태로 변경 */
    UserMissionResponse completeMission(Long userMissionId);
}
