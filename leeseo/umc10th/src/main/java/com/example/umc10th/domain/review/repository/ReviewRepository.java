package com.example.umc10th.domain.review.repository;

import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.review.entity.Review;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("""
        select distinct r from Review r
         left join fetch r.reviewPhotoList rp
                where r.store = :store
    """)
    List<Review> getReviewsByStore(Store store);

    Store store(Store store);

    Slice<Review> findReviewsByStore_IdAndIdLessThanOrderByIdDesc(Long storeId, Long idCursor, PageRequest pageRequest);
    Slice<Review> findReviewsByStore_IdOrderByIdDesc(Long storeId, PageRequest pageRequest);

    @Query("""
        SELECT r
        FROM Review r
        WHERE r.store.id = :storeId
          AND (
                r.rate < :rateCursor
                OR (r.rate = :rateCursor AND r.id < :idCursor)
          )
        ORDER BY r.rate DESC, r.id DESC
    """)
    Slice<Review> findReviewsByRateCursor(Long storeId,  Double rateCursor, Long idCursor, PageRequest pageRequest);

    @Query("""
        SELECT r
        FROM Review r
        WHERE r.store.id = :storeId
        ORDER BY r.rate DESC, r.id DESC
    """)
    Slice<Review> findReviewsOrderByRate(Long storeId, PageRequest pageRequest);
}
