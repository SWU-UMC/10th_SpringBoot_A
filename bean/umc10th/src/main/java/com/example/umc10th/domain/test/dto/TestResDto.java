package com.example.umc10th.domain.test.dto;

public class TestResDto {

    public record HealthResponse(
            String status,
            String message
    ) {
    }
}