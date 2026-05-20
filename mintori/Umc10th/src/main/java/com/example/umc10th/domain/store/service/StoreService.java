package com.example.umc10th.domain.store.service;

import com.example.umc10th.domain.store.dto.request.StoreRequest;
import com.example.umc10th.domain.store.dto.response.StorePageResponse;
import com.example.umc10th.domain.store.dto.response.StoreResponse;

public interface StoreService {

    StoreResponse addStore(StoreRequest request);

    StorePageResponse getStoresByRegion(Long regionId, int page, int size);
}
