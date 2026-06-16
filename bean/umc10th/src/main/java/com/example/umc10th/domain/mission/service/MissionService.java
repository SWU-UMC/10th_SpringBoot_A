package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.mission.dto.MissionReqDto;
import com.example.umc10th.domain.mission.dto.MissionResDto;
import com.example.umc10th.domain.mission.enums.MemberMissionStatus;

public interface MissionService {
    MissionResDto.MissionInfo getMission(Long missionId);

    MissionResDto.MissionInfo createMission(MissionReqDto.Create request);

    MissionResDto.MissionPageResponse<MissionResDto.MemberMissionPageItem> getMemberMissions(
            Long memberId,
            MemberMissionStatus status,
            Integer page
    );

    MissionResDto.MissionPageResponse<MissionResDto.HomeMissionPageItem> getHomeMissions(
            Long regionId,
            Long memberId,
            Integer page
    );

    MissionResDto.MissionPageResponse<MissionResDto.MemberMissionPageItem> getMyChallengingMissions(
            MissionReqDto.MyChallengingMissionsRequest request
    );
}
