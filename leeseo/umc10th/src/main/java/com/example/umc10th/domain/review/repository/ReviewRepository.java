package com.example.umc10th.domain.review.repository;

import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.review.entity.Review;
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
}
