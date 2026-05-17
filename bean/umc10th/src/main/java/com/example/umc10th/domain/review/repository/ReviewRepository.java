package com.example.umc10th.domain.review.repository;

import com.example.umc10th.domain.review.entity.Review;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @EntityGraph(attributePaths = {"store"})
    @Query("""
            select r
            from Review r
            where r.member.id = :memberId
            order by r.id desc
            """)
    List<Review> findMyReviewsByIdCursorFirst(
            @Param("memberId") Long memberId,
            Pageable pageable
    );

    @EntityGraph(attributePaths = {"store"})
    @Query("""
            select r
            from Review r
            where r.member.id = :memberId
              and r.id < :cursorId
            order by r.id desc
            """)
    List<Review> findMyReviewsByIdCursorNext(
            @Param("memberId") Long memberId,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );

    @EntityGraph(attributePaths = {"store"})
    @Query("""
            select r
            from Review r
            where r.member.id = :memberId
            order by r.score desc, r.id desc
            """)
    List<Review> findMyReviewsByScoreCursorFirst(
            @Param("memberId") Long memberId,
            Pageable pageable
    );

    @EntityGraph(attributePaths = {"store"})
    @Query("""
            select r
            from Review r
            where r.member.id = :memberId
              and (r.score < :cursorScore or (r.score = :cursorScore and r.id < :cursorId))
            order by r.score desc, r.id desc
            """)
    List<Review> findMyReviewsByScoreCursorNext(
            @Param("memberId") Long memberId,
            @Param("cursorScore") Float cursorScore,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );
}
