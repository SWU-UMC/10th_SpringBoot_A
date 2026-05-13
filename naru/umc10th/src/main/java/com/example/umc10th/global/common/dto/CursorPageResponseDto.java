package com.example.umc10th.global.common.dto;

import lombok.Builder;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.function.Function;

@Builder
public record CursorPageResponseDto<T>(
        List<T> content,
        Boolean hasNext,
        Long nextCursor
) {

    public static <T> CursorPageResponseDto<T> of(
            Slice<T> slice,
            Function<T, Long> cursorExtractor
    ) {
        List<T> content = slice.getContent();
        Long nextCursor = content.isEmpty() ? null : cursorExtractor.apply(content.get(content.size() - 1));

        return CursorPageResponseDto.<T>builder()
                .content(content)
                .hasNext(slice.hasNext())
                .nextCursor(nextCursor)
                .build();
    }
}
