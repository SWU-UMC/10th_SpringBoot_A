package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.*;
import com.example.umc10th.domain.mission.enums.Address;
import com.example.umc10th.domain.mission.enums.Status;
import com.example.umc10th.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc10th.domain.mission.service.MissionService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.security.entity.AuthMember;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            @AuthenticationPrincipal AuthMember member
            ) {
        Achievement response = missionService.getMissionAchievement(location, member.getMember().getId());
        return ApiResponse.onSuccess(MissionSuccessCode.ACHIEVEMENT_GET_OK, response);
    }

    @GetMapping("/missions")
    public ApiResponse<MissionResDto.Pagination<MissionInfo>> getMissionList(
            @RequestParam Address location,
            @RequestParam Integer pageSize,
            @RequestParam Integer pageNumber,
            @RequestParam (required = false) String sort
    ) {
        MissionResDto.Pagination<MissionInfo> response = missionService.getMissionList(location, pageSize, pageNumber, sort);
        return ApiResponse.onSuccess(MissionSuccessCode.MISSION_LIST_GET_OK, response);
    }

    @PostMapping("/members/me/missions/{missionId}")
    public ApiResponse<Void> saveMemberMission(
            @PathVariable Long missionId,
            @AuthenticationPrincipal AuthMember member
    ) {
        missionService.saveMemberMission(missionId,member.getMember().getId());
        return ApiResponse.onSuccess(MissionSuccessCode.MEMBER_MISSION_POST_OK,null);
    }

    @GetMapping("/members/me/missions")
    public ApiResponse<MissionResDto.Pagination<MemberMissionInfo>> getMyMissions(
            @RequestParam Status status,
            @AuthenticationPrincipal AuthMember member,
            @RequestParam Integer pageSize,
            @RequestParam Integer pageNumber,
            @RequestParam (required = false) String sort
    ) {
        MissionResDto.Pagination<MemberMissionInfo> response = missionService.getMyMissionList(status, member.getMember().getId(), pageSize, pageNumber, sort);
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
