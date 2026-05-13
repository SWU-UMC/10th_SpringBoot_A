package com.example.umc10th.domain.review.repository;

import com.example.umc10th.domain.review.entity.ReviewAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ReviewAnswerRepository extends JpaRepository<ReviewAnswer, Long> {

    List<ReviewAnswer> findByReview_IdInOrderByReview_IdAscCreatedAtDescIdDesc(Collection<Long> reviewIds);
}
