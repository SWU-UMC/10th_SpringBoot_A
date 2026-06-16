package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.mission.entity.Mission;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
                    or not exists (
                        select 1
                        from MemberMission mm
                        where mm.member.id = :memberId
                          and mm.mission.id = m.id
                    )
              )
            order by m.id desc
            """)
    Slice<Mission> findChallengeableMissionsByRegion(
            @Param("regionId") Long regionId,
            @Param("memberId") Long memberId,
            Pageable pageable
    );
}
