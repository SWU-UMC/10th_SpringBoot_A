package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.Achievement;
import com.example.umc10th.domain.mission.dto.MemberMissionInfo;
import com.example.umc10th.domain.mission.dto.MissionInfo;
import com.example.umc10th.domain.mission.dto.OwnerNumber;
import com.example.umc10th.domain.mission.enums.Address;
import com.example.umc10th.domain.mission.enums.Status;
import com.example.umc10th.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc10th.domain.mission.service.MissionService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Tag(name = "Mission", description = "미션 관련 API")
public class MissionController implements MissionControllerDocs{

    private final MissionService missionService;

    @GetMapping("/missions/achievement-rate")
    public ApiResponse<Achievement> getMissionAchievement(
            @RequestParam Address location,
            @RequestParam Long memberId
    ) {
        Achievement response = missionService.getMissionAchievement(location, memberId);
        return ApiResponse.onSuccess(MissionSuccessCode.ACHIEVEMENT_GET_OK, response);
    }

    @GetMapping("/missions")
    public ApiResponse<Slice<MissionInfo>> getMissionList(
            @RequestParam Address location,
            @RequestParam Long cursor
    ) {
        Slice<MissionInfo> response = missionService.getMissionList(location, cursor);
        return ApiResponse.onSuccess(MissionSuccessCode.MISSION_LIST_GET_OK, response);
    }

    @PostMapping("/members/me/missions/{missionId}")
    public ApiResponse<Void> saveMemberMission(
            @PathVariable Long missionId,
            @RequestParam Long memberId
    ) {
        missionService.saveMemberMission(missionId,memberId);
        return ApiResponse.onSuccess(MissionSuccessCode.MEMBER_MISSION_POST_OK,null);
    }

    @GetMapping("/members/me/missions")
    public ApiResponse<Slice<MemberMissionInfo>> getMyMissions(
            @RequestParam Status status,
            @RequestParam Long memberId,
            @RequestParam Long cursor
    ) {
        Slice<MemberMissionInfo> response = missionService.getMyMissionList(status, memberId, cursor);
        return ApiResponse.onSuccess(MissionSuccessCode.MY_MISSION_LIST_GET_OK, response);
    }

    @PostMapping("/missions/{memberMissionId}/success")
    public ApiResponse<Void> postSuccessRequest(
            @PathVariable Long memberMissionId
    ) {
        missionService.postSuccessRequest(memberMissionId);
        return ApiResponse.onSuccess(MissionSuccessCode.MISSION_SUCCESS_REQUEST_OK, null);
    }

    @GetMapping("/missions/{memberMissionId}/success")
    public ApiResponse<OwnerNumber> getOwnerNumber(
            @PathVariable Long memberMissionId
    ) {
        OwnerNumber response = missionService.getOwnerNumber(memberMissionId);
        return ApiResponse.onSuccess(MissionSuccessCode.OWNER_NUMBER_GET_OK, response);
    }

}
