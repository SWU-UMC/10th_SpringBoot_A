package com.example.umc10th.domain.test.controller;

import com.example.umc10th.domain.test.dto.TestResDto;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.status.SuccessStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<TestResDto.HealthResponse>> health() {
        TestResDto.HealthResponse result = new TestResDto.HealthResponse("UP", "Test API is working.");
        return ResponseEntity.ok(ApiResponse.of(SuccessStatus.OK, result));
    }
}