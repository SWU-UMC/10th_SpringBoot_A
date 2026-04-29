package com.example.umc10th.domain.store.controller;

import com.example.umc10th.domain.store.dto.StoreResDto;
import com.example.umc10th.domain.store.service.StoreService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/{storeId}")
    public ResponseEntity<ApiResponse<StoreResDto.StoreInfo>> getStore(@PathVariable Long storeId) {
        return ResponseEntity.ok(ApiResponse.onSuccess(storeService.getStore(storeId)));
    }
}
