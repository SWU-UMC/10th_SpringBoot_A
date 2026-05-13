package com.example.umc10th.global.common.dto;

import lombok.Builder;
import org.springframework.data.domain.Slice;

import java.util.List;

@Builder
public record OffsetPageResponseDto<T>(
        List<T> content,
        Integer page,
        Integer size,
        Boolean hasNext
) {

    public static <T> OffsetPageResponseDto<T> of(Slice<T> slice) {
        return OffsetPageResponseDto.<T>builder()
                .content(slice.getContent())
                .page(slice.getNumber())
                .size(slice.getSize())
                .hasNext(slice.hasNext())
                .build();
    }
}
