package com.ssafy12.tripchoice.hotplace.dto;

import jakarta.annotation.Nullable;
import lombok.Builder;

@Builder
public record HotPlaceSearchDto(Integer page,
                                Integer size,
                                @Nullable String sido, //필터링
                                @Nullable String gugun, //필터링
                                @Nullable Integer content_type_id, //필터링
                                @Nullable String key,
                                @Nullable String word, // 검색어 검색
                                @Nullable Integer radius // 반경 검색
) {
}
