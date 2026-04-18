package com.example.umc10th.domain.store.dto;

public class StoreResDto {

    public record StoreInfo(
            Long storeId,
            String name,
            String address,
            Float score,
            String regionName,
            String categoryName
    ) {
    }
}