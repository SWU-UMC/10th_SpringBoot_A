package com.example.umc10th.domain.review.repository;

import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.entity.ReviewPhoto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewPhotoRepository extends JpaRepository<ReviewPhoto, Long> {

    @Query("""
            SELECT rp
            FROM ReviewPhoto rp
            JOIN rp.store s
            WHERE s.id = :storeId
    """)
    List<ReviewPhoto> getReviewPhotoByStore(Long storeId);

    Slice<ReviewPhoto> findReviewPhotosByStore_IdAndIdLessThanOrderByIdDesc(Long storeId, Long idCursor, PageRequest pageRequest);
    Slice<ReviewPhoto> findReviewPhotosByStore_IdOrderByIdDesc(Long storeId, PageRequest pageRequest);

    @Query("""
              SELECT rp
             FROM ReviewPhoto rp
             JOIN rp.store s
             JOIN rp.review r
             WHERE s.id = :storeId
               AND (
                    r.rate < :rateCursor
                    OR (r.rate = :rateCursor AND rp.id < :idCursor)
               )
             ORDER BY r.rate DESC, rp.id DESC
    """)
    Slice<ReviewPhoto> findReviewPhotosByRateCursor(Long storeId, Double rateCursor, Long idCursor, PageRequest pageRequest);
    @Query("""
             SELECT rp
             FROM ReviewPhoto rp
             JOIN rp.store s
             JOIN rp.review r
             WHERE s.id = :storeId
             ORDER BY r.rate DESC, rp.id DESC
    """)
    Slice<ReviewPhoto> findReviewPhotosOrderByRate(Long storeId, PageRequest pageRequest);
}
