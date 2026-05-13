package com.example.umc10th.domain.review.repository;

import com.example.umc10th.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByUserId(Long userId, Pageable pageable);

    Page<Review> findByStoreId(Long storeId, Pageable pageable);

    long countByUserId(Long userId);

    /**
     * [필수 2] 내 리뷰 - ID 커서 정렬 (id DESC, 사진 부분 제외)
     * 첫 페이지 : cursorId == null  → 전체 중 최신 id 순으로 size + 1 개
     * 이후 페이지: id < cursorId 인 것 중 size + 1 개
     */
    @Query("""
            select r
              from Review r
             where r.user.id = :userId
               and (:cursorId is null or r.id < :cursorId)
             order by r.id desc
            """)
    List<Review> findMyReviewsByCursorId(
            @Param("userId") Long userId,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );

    /**
     * [필수 2] 내 리뷰 - 별점 커서 정렬 (score DESC, id DESC tie-breaker, 사진 부분 제외)
     * 첫 페이지  : cursorScore == null && cursorId == null
     * 이후 페이지: (score < cursorScore) OR (score = cursorScore AND id < cursorId)
     */
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
    List<Review> findMyReviewsByCursorScore(
            @Param("userId") Long userId,
            @Param("cursorScore") BigDecimal cursorScore,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );
}
