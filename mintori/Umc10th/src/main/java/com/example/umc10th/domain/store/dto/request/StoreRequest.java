package com.example.umc10th.domain.store.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record StoreRequest(
        @NotNull(message = "지역 ID를 입력해주세요")
        Long regionId,

        @NotBlank(message = "가게 이름을 입력해주세요")
        @Size(max = 100)
        String name,

        @Size(max = 255)
        String address
) {
}
