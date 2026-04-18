package com.example.umc10th.domain.store.service;

import com.example.umc10th.domain.store.dto.StoreResDto;

public interface StoreService {
    StoreResDto.StoreInfo getStore(Long storeId);
}