package com.example.umc10th.domain.review.repository;

import com.example.umc10th.domain.review.entity.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {

    List<ReviewImage> findByReview_IdInOrderByReview_IdAscSequenceAsc(Collection<Long> reviewIds);
}
