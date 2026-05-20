package com.example.umc10th.domain.store.controller;

import com.example.umc10th.domain.store.dto.request.StoreRequest;
import com.example.umc10th.domain.store.dto.response.StorePageResponse;
import com.example.umc10th.domain.store.dto.response.StoreResponse;
import com.example.umc10th.domain.store.service.StoreService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.GeneralSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    public ApiResponse<StoreResponse> addStore(@Valid @RequestBody StoreRequest request) {
        return ApiResponse.onSuccess(GeneralSuccessCode.CREATED, storeService.addStore(request));
    }

    @GetMapping("/regions/{regionId}")
    public ApiResponse<StorePageResponse> getStoresByRegion(
            @PathVariable Long regionId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.onSuccess(
                GeneralSuccessCode.OK,
                storeService.getStoresByRegion(regionId, page, size)
        );
    }
}
