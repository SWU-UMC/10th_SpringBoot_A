package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.MissionResDto;
import com.example.umc10th.domain.mission.enums.Address;
import com.example.umc10th.domain.mission.enums.Status;
import com.example.umc10th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface MissionControllerDocs {

    @Operation(
            summary = "지역별 미션 달성률 조회",
            description = "해당 지역의 미션 달성률을 조회합니다."
    )
    public ApiResponse<MissionResDto.Achievement> getMissionAchievement(
            @RequestParam Address location
    );

    @Operation(
            summary = "지역별 미션 조회",
            description = "해당 지역의 미션 목록을 조회합니다."
    )
    public ApiResponse<MissionResDto.StoreList> getMissionList(
            @RequestParam Address location
    );

    @Operation(
            summary = "미션 도전",
            description = "해당 미션 도전을 생성합니다."
    )
    public ApiResponse<Void> saveMemberMission(
            @PathVariable Long missionId
    );

    @Operation(
            summary = "진행중/진행완료 미션 조회",
            description = "내 미션 목록을 진행 상태별로 조회합니다."
    )
    public ApiResponse<MissionResDto.StoreList> getMyMissions(
            @RequestParam Status status
    );

    @Operation(
            summary = "미션 성공 요청",
            description = "미션 성공 요청을 합니다."
    )
    public ApiResponse<Void> postSuccessRequest(
            @PathVariable Long missionId
    );

    @Operation(
            summary = "사장님 구분번호 조회",
            description = "사장님 구분번호를 조회 합니다."
    )
    public ApiResponse<MissionResDto.OwnerNumber> getOwnerNumber(
            @PathVariable Long missionId
    );
}
