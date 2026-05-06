package com.example.umc10th.domain.region.repository;

import com.example.umc10th.domain.region.entity.RegionProgress;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RegionProgressRepository extends JpaRepository<RegionProgress, Long> {

    @Query("""
            select rp
            from RegionProgress rp
            join fetch rp.region r
            where rp.user.id = :userId
              and r.id = :regionId
            order by rp.id desc
            """)
    Slice<RegionProgress> findLatestByUserIdAndRegionId(
            @Param("userId") Long userId,
            @Param("regionId") Long regionId,
            Pageable pageable
    );
}
