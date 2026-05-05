package com.example.umc10th.domain.store.converter;

import com.example.umc10th.domain.store.dto.request.StoreRequest;
import com.example.umc10th.domain.store.dto.response.StorePageResponse;
import com.example.umc10th.domain.store.dto.response.StoreResponse;
import com.example.umc10th.domain.store.entity.Region;
import com.example.umc10th.domain.store.entity.Store;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public class StoreConverter {

    private StoreConverter() {
    }

    public static Store toStore(StoreRequest request, Region region) {
        return Store.builder()
                .name(request.name())
                .address(request.address())
                .region(region)
                .score(BigDecimal.ZERO)
                .build();
    }

    public static StoreResponse toStoreResponse(Store store) {
        return new StoreResponse(
                store.getId(),
                store.getRegion().getId(),
                store.getName(),
                store.getAddress(),
                store.getScore(),
                store.getCreatedAt()
        );
    }

    public static StorePageResponse toStorePageResponse(Page<Store> page) {
        List<StoreResponse> stores = page.getContent().stream()
                .map(StoreConverter::toStoreResponse)
                .toList();
        return new StorePageResponse(
                stores,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.hasNext()
        );
    }
}
