package com.example.umc10th.domain.review.repository;

import com.example.umc10th.domain.review.entity.ReviewPhoto;
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
}
