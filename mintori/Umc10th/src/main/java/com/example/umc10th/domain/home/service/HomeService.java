package com.example.umc10th.domain.home.service;

import com.example.umc10th.domain.home.dto.response.HomeResponse;

public interface HomeService {

    /** 홈 화면 - 유저 정보 + 특정 지역에서 도전 가능한 미션 목록(페이징) */
    HomeResponse getHome(Long userId, Long regionId, int page, int size);
}
