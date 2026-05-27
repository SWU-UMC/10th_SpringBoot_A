package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.mission.entity.MemberMission;
import com.example.umc10th.domain.mission.enums.MemberMissionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    @EntityGraph(attributePaths = {"mission", "mission.store"})
    @Query("""
            select mm
            from MemberMission mm
            where mm.member.id = :memberId
              and mm.status = :status
            order by mm.id desc
            """)
    Page<MemberMission> findMemberMissionsByStatus(
            @Param("memberId") Long memberId,
            @Param("status") MemberMissionStatus status,
            Pageable pageable
    );

    @Query("""
            select coalesce(sum(mm.mission.rewardPoint), 0)
            from MemberMission mm
            where mm.member.id = :memberId
              and mm.status = :status
            """)
    Integer sumRewardPointByMemberIdAndStatus(
            @Param("memberId") Long memberId,
            @Param("status") MemberMissionStatus status
    );
}
