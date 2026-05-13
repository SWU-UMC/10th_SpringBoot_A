package com.example.umc10th.domain.review.repository;

import com.example.umc10th.domain.review.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    boolean existsByUserMissionId(Long userMissionId);

    @Query("""
            select r
            from Review r
            join fetch r.user u
            where u.id = :userId
              and (:cursorId is null or r.id < :cursorId)
            order by r.id desc
            """)
    Slice<Review> findMyReviewsOrderById(
            @Param("userId") Long userId,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );

    @Query("""
            select r
            from Review r
            join fetch r.user u
            where u.id = :userId
              and (
                (:cursorRating is null and :cursorId is null)
                or (
                    :cursorRating is not null
                    and (
                        r.rating < :cursorRating
                        or (:cursorId is not null and r.rating = :cursorRating and r.id < :cursorId)
                    )
                )
              )
            order by r.rating desc, r.id desc
            """)
    Slice<Review> findMyReviewsOrderByRating(
            @Param("userId") Long userId,
            @Param("cursorRating") Double cursorRating,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );
}
