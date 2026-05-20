package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.mission.entity.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    /** 홈 화면용 - 특정 지역의 모든 미션 페이징 (도전 이력이 없는 신규 유저용) */
    Page<Mission> findByStore_Region_Id(Long regionId, Pageable pageable);

    /** 홈 화면용 - 특정 지역에서 도전 이력이 있는 유저용 (이미 도전중/완료한 미션 제외) */
    Page<Mission> findByStore_Region_IdAndIdNotIn(Long regionId, Collection<Long> excludedIds, Pageable pageable);
}
