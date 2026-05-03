package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.dto.Achievement;
import com.example.umc10th.domain.mission.dto.MemberMissionInfo;
import com.example.umc10th.domain.mission.dto.MissionInfo;
import com.example.umc10th.domain.mission.dto.OwnerNumber;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.mission.enums.Address;
import com.example.umc10th.domain.mission.enums.Status;
import com.example.umc10th.domain.mission.exception.MissionException;
import com.example.umc10th.domain.mission.exception.code.MissionErrorCode;
import com.example.umc10th.domain.mission.repository.MemberMissionRepository;
import com.example.umc10th.domain.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final MemberRepository memberRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final MissionRepository missionRepository;

    public Achievement getMissionAchievement(Address address, Long memberId) {
        Member member = memberRepository.findMemberById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        return memberMissionRepository.getTotalCountAndSuccessCount(address, member);
    }

    public Slice<MissionInfo> getMissionList(Address address, Long cursor) {
        Pageable pageable = PageRequest.of(0, 10);
        return missionRepository.getMissionByAddress(address, cursor, pageable);
    }

    @Transactional
    public void saveMemberMission(Long missionId, Long memberId) {
        Member member = memberRepository.findMemberById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        Mission mission = missionRepository.findMissionById(missionId)
                .orElseThrow(() -> new MissionException(MissionErrorCode.MISSION_NOT_FOUND));
        MemberMission memberMission = MissionConverter.toMemberMission(member, mission);
        memberMissionRepository.save(memberMission);
    }

    public Slice<MemberMissionInfo> getMyMissionList(Status status, Long memberId, Long cursor) {
        Pageable pageable = PageRequest.of(0, 10);
        return memberMissionRepository.getMissionByStatus(status, cursor, pageable, memberId);
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
