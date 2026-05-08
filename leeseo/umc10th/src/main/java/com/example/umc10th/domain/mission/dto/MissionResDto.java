package com.example.umc10th.domain.mission.dto;

import lombok.Builder;

import java.util.List;

public class MissionResDto {

    @Builder
    public record Pagination<T>(
            List<T> data,
            Integer pageNumber,
            Integer pageSize
    ){}
}
