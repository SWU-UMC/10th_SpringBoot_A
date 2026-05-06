package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.mission.entity.Mission;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    @Query("""
            select m
            from Mission m
            join fetch m.store s
            left join fetch s.foodCategory fc
            where s.region.id = :regionId
              and m.status = true
              and (m.endAt is null or m.endAt > current timestamp)
              and (:cursor is null or m.id < :cursor)
              and not exists (
                  select 1
                  from UserMission um
                  where um.user.id = :userId
                    and um.mission.id = m.id
              )
            order by m.id desc
            """)
    Slice<Mission> findAvailableMissionsByCursor(
            @Param("userId") Long userId,
            @Param("regionId") Long regionId,
            @Param("cursor") Long cursor,
            Pageable pageable
    );
}
