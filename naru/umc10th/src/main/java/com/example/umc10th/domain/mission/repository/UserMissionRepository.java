package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.mission.entity.enums.MissionStatus;
import com.example.umc10th.domain.mission.entity.mapping.UserMission;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserMissionRepository extends JpaRepository<UserMission, Long> {

    @Query("""
            select um
            from UserMission um
            join fetch um.mission m
            join fetch m.store s
            where um.user.id = :userId
              and (:status is null or um.status = :status)
              and (:regionId is null or s.region.id = :regionId)
              and (:cursor is null or um.id < :cursor)
            order by um.id desc
            """)
    Slice<UserMission> findMyMissionsByCursor(
            @Param("userId") Long userId,
            @Param("status") MissionStatus status,
            @Param("regionId") Long regionId,
            @Param("cursor") Long cursor,
            Pageable pageable
    );
}
