package com.example.umc10th.domain.store.converter;

import com.example.umc10th.domain.store.dto.StoreResDto;
import com.example.umc10th.domain.store.entity.Store;

public final class StoreConverter {

    private StoreConverter() {
    }

    public static StoreResDto.StoreInfo toStoreInfo(Store store) {
        return new StoreResDto.StoreInfo(
                store.getId(),
                store.getName(),
                store.getAddress(),
                store.getScore(),
                store.getRegion().getName(),
                store.getCategory().getName()
        );
    }
}