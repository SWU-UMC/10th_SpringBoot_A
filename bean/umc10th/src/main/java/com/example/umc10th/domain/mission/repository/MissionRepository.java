package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.mission.entity.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    @EntityGraph(attributePaths = {"store"})
    @Query("""
            select m
            from Mission m
            where m.store.region.id = :regionId
              and (
                    :memberId is null
                    or m.id not in (
                        select mm.mission.id
                        from MemberMission mm
                        where mm.member.id = :memberId
                    )
              )
            order by m.id desc
            """)
    Page<Mission> findChallengeableMissionsByRegion(
            @Param("regionId") Long regionId,
            @Param("memberId") Long memberId,
            Pageable pageable
    );
}
