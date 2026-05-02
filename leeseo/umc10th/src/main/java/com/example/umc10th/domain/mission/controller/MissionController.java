package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.MissionResDto;
import com.example.umc10th.domain.mission.enums.Address;
import com.example.umc10th.domain.mission.enums.Status;
import com.example.umc10th.domain.member.enums.FoodType;
import com.example.umc10th.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc10th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController("/api/vi")
@Tag(name = "Mission", description = "미션 관련 API")
public class MissionController implements MissionControllerDocs{

    @GetMapping("/missions/achievement-rate")
    public ApiResponse<MissionResDto.Achievement> getMissionAchievement(
            @RequestParam Address location
    ) {
        // 서비스 로직
        MissionResDto.Achievement response = new MissionResDto.Achievement(10,7);
        return ApiResponse.onSuccess(MissionSuccessCode.ACHIEVEMENT_GET_OK, response);
    }

    @GetMapping("/missions")
    public ApiResponse<MissionResDto.StoreList> getMissionList(
            @RequestParam Address location
    ) {
        // 서비스 로직
        MissionResDto.StoreInfo store = new MissionResDto.StoreInfo("반이학생 마라탕", FoodType.CHINESE, LocalDate.now(), 10000, 500);
        MissionResDto.StoreList response = new MissionResDto.StoreList(List.of(store));
        return ApiResponse.onSuccess(MissionSuccessCode.MISSION_LIST_GET_OK, response);
    }

    @PostMapping("/members/me/missions/{missionId}")
    public ApiResponse<Void> saveMemberMission(
            @PathVariable Long missionId
    ) {
        // 서비스 로직
        return ApiResponse.onSuccess(MissionSuccessCode.MEMBER_MISSION_POST_OK,null);
    }

    @GetMapping("/members/me/missions")
    public ApiResponse<MissionResDto.StoreList> getMyMissions(
            @RequestParam Status status
    ) {
        // 서비스 로직
        MissionResDto.StoreInfo store = new MissionResDto.StoreInfo("반이학생 마라탕", FoodType.CHINESE, LocalDate.now(), 10000, 500);
        MissionResDto.StoreList response = new MissionResDto.StoreList(List.of(store));
        return ApiResponse.onSuccess(MissionSuccessCode.MY_MISSION_LIST_GET_OK, response);
    }

    @PostMapping("/missions/{missionId}/success")
    public ApiResponse<Void> postSuccessRequest(
            @PathVariable Long missionId
    ) {
        // 서비스 로직
        return ApiResponse.onSuccess(MissionSuccessCode.MISSION_SUCCESS_REQUEST_OK, null);
    }

    @GetMapping("/missions/{missionId}/success")
    public ApiResponse<MissionResDto.OwnerNumber> getOwnerNumber(
            @PathVariable Long missionId
    ) {
        // 서비스 로직
        MissionResDto.OwnerNumber response = new MissionResDto.OwnerNumber(1234232);
        return ApiResponse.onSuccess(MissionSuccessCode.OWNER_NUMBER_GET_OK, response);
    }

}
