package com.example.umc10th.domain.review.repository;

import com.example.umc10th.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByUserId(Long userId, Pageable pageable);

    Page<Review> findByStoreId(Long storeId, Pageable pageable);

    long countByUserId(Long userId);

    @Query("""
            select r
              from Review r
             where r.user.id = :userId
               and (:cursorId is null or r.id < :cursorId)
             order by r.id desc
            """)
    Slice<Review> findMyReviewsByCursorId(
            @Param("userId") Long userId,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );

    @Query("""
            select r
              from Review r
             where r.user.id = :userId
               and (
                    :cursorScore is null
                 or r.score < :cursorScore
                 or (r.score = :cursorScore and r.id < :cursorId)
               )
             order by r.score desc, r.id desc
            """)
    Slice<Review> findMyReviewsByCursorScore(
            @Param("userId") Long userId,
            @Param("cursorScore") BigDecimal cursorScore,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );
}
