package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.mission.dto.MissionResponseDto;
import com.example.umc10th.domain.mission.entity.enums.MissionStatus;
import com.example.umc10th.global.common.dto.CursorPageResponseDto;

public interface MissionService {

    CursorPageResponseDto<MissionResponseDto.MissionPreviewDto> getMyMissions(
            Long userId,
            MissionStatus status,
            Long regionId,
            Long cursor,
            int size
    );
}
