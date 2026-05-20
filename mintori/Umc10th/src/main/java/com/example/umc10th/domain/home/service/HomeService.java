package com.example.umc10th.domain.home.service;

import com.example.umc10th.domain.home.dto.response.HomeResponse;

public interface HomeService {

    HomeResponse getHome(Long userId, Long regionId, int page, int size);
}
