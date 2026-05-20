package com.example.umc10th.domain.review.dto.response;

import java.math.BigDecimal;
import java.util.List;

/**
 * 내가 생성한 리뷰 커서 기반 페이지네이션 응답
 * - reviews     : 이번 페이지 리뷰 목록
 * - size        : 요청한 페이지 크기
 * - hasNext     : 다음 페이지 존재 여부
 * - nextCursorId    : 다음 요청에 넘길 cursorId   (없으면 null)
 * - nextCursorScore : 다음 요청에 넘길 cursorScore (없거나 ID 정렬이면 null)
 */
public record MyReviewCursorResponse(
        List<ReviewResponse> reviews,
        Integer size,
        Boolean hasNext,
        Long nextCursorId,
        BigDecimal nextCursorScore
) {
}
