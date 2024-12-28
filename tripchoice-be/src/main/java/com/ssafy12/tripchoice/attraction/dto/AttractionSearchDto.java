package com.ssafy12.tripchoice.attraction.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AttractionSearchDto(
        @NotNull Integer page,
        @NotNull Integer size,
        @Nullable Integer sido_code, //필터링
        @Nullable Integer gugun_code, //필터링
        @Nullable Integer content_type_id, //필터링
        @Nullable String key,
        @Nullable String word, // 검색어 검색
        @Nullable Integer radius // 반경 검색
) {
}
