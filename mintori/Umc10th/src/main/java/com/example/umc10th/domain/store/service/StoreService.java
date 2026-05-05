package com.example.umc10th.domain.store.service;

import com.example.umc10th.domain.store.dto.request.StoreRequest;
import com.example.umc10th.domain.store.dto.response.StorePageResponse;
import com.example.umc10th.domain.store.dto.response.StoreResponse;

public interface StoreService {

    /** 특정 지역에 가게 추가 */
    StoreResponse addStore(StoreRequest request);

    /** 특정 지역의 가게 목록 페이징 조회 */
    StorePageResponse getStoresByRegion(Long regionId, int page, int size);
}
