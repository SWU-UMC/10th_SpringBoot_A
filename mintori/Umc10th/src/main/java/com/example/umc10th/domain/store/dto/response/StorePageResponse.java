package com.example.umc10th.domain.store.dto.response;

import java.util.List;

public record StorePageResponse(
        List<StoreResponse> stores,
        Integer page,
        Integer size,
        Long totalElements,
        Integer totalPages,
        Boolean hasNext
) {
}
