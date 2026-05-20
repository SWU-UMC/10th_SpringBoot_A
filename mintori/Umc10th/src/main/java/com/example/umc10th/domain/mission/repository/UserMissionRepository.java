package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.mission.entity.UserMission;
import com.example.umc10th.domain.mission.enums.MissionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserMissionRepository extends JpaRepository<UserMission, Long> {

    boolean existsByUserIdAndMissionId(Long userId, Long missionId);

    Page<UserMission> findByUserIdAndStatus(Long userId, MissionStatus status, Pageable pageable);

    long countByUserIdAndStatus(Long userId, MissionStatus status);

    /** 홈 화면용 - 유저가 이미 도전중이거나 완료한 미션 ID 목록 (도전 가능 미션에서 제외할 용도) */
    @Query("select um.mission.id from UserMission um where um.user.id = :userId")
    List<Long> findMissionIdsByUserId(@Param("userId") Long userId);
}
