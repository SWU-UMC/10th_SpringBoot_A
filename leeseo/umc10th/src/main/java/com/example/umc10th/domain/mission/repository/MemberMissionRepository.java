package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.dto.Achievement;
import com.example.umc10th.domain.mission.dto.MemberMissionInfo;
import com.example.umc10th.domain.mission.dto.MissionInfo;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.mission.enums.Address;
import com.example.umc10th.domain.mission.enums.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    Optional<MemberMission> findMemberMissionById(Long id);

    @Query("""
         SELECT new com.example.umc10th.domain.mission.dto.Achievement(
                COUNT(mm),
                SUM(CASE WHEN mm.status = 'SUCCESS' THEN 1 ELSE 0 END)
            )
            FROM MemberMission mm
            WHERE mm.member = :member
            AND mm.mission.store.location.name = :address
    """)
    Achievement getTotalCountAndSuccessCount(Address address, Member member);

    @Query("""
         SELECT new com.example.umc10th.domain.mission.dto.MemberMissionInfo(
                m.id, mm.id,  s.name, s.storeType, m.deadline, m.minCost, m.rewardsPoint, mm.status
             )
             FROM MemberMission mm
             JOIN mm.mission m
             JOIN m.store s
             WHERE mm.status = :status
             AND mm.member.id = :memberId
             AND (:cursor IS NULL OR m.id < :cursor)
             ORDER BY m.id DESC
    """)
    Slice<MemberMissionInfo> getMissionByStatus(Status status, Long cursor, Pageable pageable,Long memberId);
}
