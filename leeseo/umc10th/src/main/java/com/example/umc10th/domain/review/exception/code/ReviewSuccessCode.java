package com.example.umc10th.domain.review.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewSuccessCode implements BaseSuccessCode {
    REVIEW_POST_OK(HttpStatus.OK,
            "REVIEW200_1",
            "리뷰가 작성되었습니다."),

    REVIEW_LIST_GET_OK(HttpStatus.OK,
            "REVIEW200_2",
            "리뷰 목록이 조회되었습니다."),

    REVIEW_PHOTO_LIST_GET_OK(HttpStatus.OK,
            "REVIEW200_3",
            "리뷰 사진 목록이 조회되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
