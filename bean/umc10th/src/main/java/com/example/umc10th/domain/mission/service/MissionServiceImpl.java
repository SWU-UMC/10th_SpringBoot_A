package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.dto.MissionReqDto;
import com.example.umc10th.domain.mission.dto.MissionResDto;
import com.example.umc10th.domain.mission.entity.MemberMission;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.enums.MemberMissionStatus;
import com.example.umc10th.domain.store.entity.Store;
import com.example.umc10th.domain.mission.exception.MissionException;
import com.example.umc10th.domain.mission.exception.code.MissionErrorCode;
import com.example.umc10th.domain.mission.repository.MemberMissionRepository;
import com.example.umc10th.domain.mission.repository.MissionRepository;
import com.example.umc10th.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionServiceImpl implements MissionService {

    private final MissionRepository missionRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final StoreRepository storeRepository;

    @Override
    public MissionResDto.MissionInfo getMission(Long missionId) {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new MissionException(MissionErrorCode.MISSION_NOT_FOUND));

        return MissionConverter.toMissionInfo(mission);
    }

    @Override
    @Transactional
    public MissionResDto.MissionInfo createMission(MissionReqDto.Create request) {
        Store store = storeRepository.findById(request.storeId())
                .orElseThrow(() -> new MissionException(MissionErrorCode.STORE_NOT_FOUND));

        Mission savedMission = missionRepository.save(MissionConverter.toEntity(request, store));
        return MissionConverter.toMissionInfo(savedMission);
    }

    @Override
    public MissionResDto.MemberMissionPageResponse getMemberMissions(Long memberId, MemberMissionStatus status, Integer page) {
        int pageNumber = Math.max(page, 1) - 1;
        PageRequest pageRequest = PageRequest.of(pageNumber, 10);

        Page<MemberMission> memberMissionPage = memberMissionRepository.findMemberMissionsByStatus(
                memberId,
                status,
                pageRequest
        );

        return MissionConverter.toMemberMissionPageResponse(memberMissionPage);
    }

    @Override
    public MissionResDto.HomeMissionPageResponse getHomeMissions(Long regionId, Long memberId, Integer page) {
        int pageNumber = Math.max(page, 1) - 1;
        PageRequest pageRequest = PageRequest.of(pageNumber, 10);

        Page<Mission> missionPage = missionRepository.findChallengeableMissionsByRegion(regionId, memberId, pageRequest);
        return MissionConverter.toHomeMissionPageResponse(missionPage);
    }

    @Override
    public MissionResDto.MemberMissionPageResponse getMyChallengingMissions(MissionReqDto.MyChallengingMissionsRequest request) {
        int pageNumber = request.page() - 1;
        PageRequest pageRequest = PageRequest.of(pageNumber, request.size());

        Page<MemberMission> memberMissionPage = memberMissionRepository.findMemberMissionsByStatus(
                request.memberId(),
                MemberMissionStatus.CHALLENGING,
                pageRequest
        );

        return MissionConverter.toMemberMissionPageResponse(memberMissionPage);
    }
}
