package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.mission.dto.MissionInfo;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.enums.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    Optional<Mission> findMissionById(Long id);

    @Query("""
         SELECT new com.example.umc10th.domain.mission.dto.MissionInfo(
                m.id,  s.name, s.storeType, m.deadline, m.minCost, m.rewardsPoint
             )
            FROM Mission m
            JOIN m.store s
            WHERE s.address = :address
    """)
    Page<MissionInfo> getMissionByAddress(Address address, PageRequest pageRequest);
}
