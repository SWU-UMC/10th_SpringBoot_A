package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.mission.entity.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    Page<Mission> findByStore_Region_Id(Long regionId, Pageable pageable);
    Page<Mission> findByStore_Region_IdAndIdNotIn(Long regionId, Collection<Long> excludedIds, Pageable pageable);
}
