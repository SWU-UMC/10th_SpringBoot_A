package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.dto.*;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.mission.enums.Address;
import com.example.umc10th.domain.mission.enums.Status;
import com.example.umc10th.domain.mission.exception.MissionException;
import com.example.umc10th.domain.mission.exception.code.MissionErrorCode;
import com.example.umc10th.domain.mission.repository.MemberMissionRepository;
import com.example.umc10th.domain.mission.repository.MissionRepository;
import com.example.umc10th.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final MemberRepository memberRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final MissionRepository missionRepository;

    public Achievement getMissionAchievement(Address address, Long memberId) {
        Member member = getMemberById(memberId);

        return memberMissionRepository.getTotalCountAndSuccessCount(address, member);
    }

    private Member getMemberById(Long memberId) {
        Member member = memberRepository.findMemberById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        return member;
    }

    public MissionResDto.Pagination<MissionInfo> getMissionList(
            Address address,
            Integer pageSize,
            Integer pageNumber,
            String sort
        ) {
        Sort sortInfo;
        if (sort != null){
            sortInfo = Sort.by(sort);
        } else {
            sortInfo = Sort.by("id").descending();
        }

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sortInfo);
        Page<MissionInfo> missionList = missionRepository.getMissionByAddress(address, pageRequest);
        return MissionConverter.toPagination(
                missionList.stream().toList(),
                missionList.getNumber(),
                missionList.getSize()
        );
    }

    @Transactional
    public void saveMemberMission(Long missionId, Long memberId) {
        Member member = getMemberById(memberId);
        Mission mission = missionRepository.findMissionById(missionId)
                .orElseThrow(() -> new MissionException(MissionErrorCode.MISSION_NOT_FOUND));
        MemberMission memberMission = MissionConverter.toMemberMission(member, mission);
        memberMissionRepository.save(memberMission);
    }

    public MissionResDto.Pagination<MemberMissionInfo> getMyMissionList(
            Status status,
            Long memberId,
            Integer pageSize,
            Integer pageNumber,
            String sort
    ) {
        Sort sortInfo;
        if (sort != null){
            sortInfo = Sort.by(sort);
        } else {
            sortInfo = Sort.by("id").descending();
        }

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sortInfo);
        Page<MemberMissionInfo> missionList = memberMissionRepository.getMissionByStatus(status, memberId, pageRequest);
        return MissionConverter.toPagination(
                missionList.stream().toList(),
                missionList.getNumber(),
                missionList.getSize()
        );
    }

    @Transactional
    public void postSuccessRequest(Long memberMissionId) {
        MemberMission memberMission = memberMissionRepository.findMemberMissionById(memberMissionId)
                .orElseThrow(() -> new MissionException(MissionErrorCode.MEMBER_MISSION_NOT_FOUND));
        // 성공 확인 로직
        memberMission.updateStatus(Status.SUCCESS);
        memberMission.updateSuccessDate(LocalDate.now());
        memberMission.updateSuccessNumber(memberMission.getMember());
    }

    public OwnerNumber getOwnerNumber(Long memberMissionId) {
        MemberMission memberMission = memberMissionRepository.findMemberMissionById(memberMissionId)
                .orElseThrow(() -> new MissionException(MissionErrorCode.MEMBER_MISSION_NOT_FOUND));
        return MissionConverter.toOwnerNumber(memberMission);
    }
}
