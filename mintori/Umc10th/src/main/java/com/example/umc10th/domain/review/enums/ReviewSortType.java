package com.example.umc10th.domain.review.enums;

/**
 * 내 리뷰 목록 조회 시 사용하는 정렬 기준
 * - ID    : 리뷰 ID 내림차순 (최신순)
 * - SCORE : 별점 내림차순 (별점 같으면 ID 내림차순)
 */
public enum ReviewSortType {
    ID,
    SCORE
}
