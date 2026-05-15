package com.example.umc10th.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthResDto {

    @Builder
    public record Id(
       Long id
    ) {}
}
