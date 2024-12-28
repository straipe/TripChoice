package com.ssafy12.tripchoice.attraction.dto;

import lombok.Builder;

@Builder
public record AttractionSearchRequestDto(
        String sido,
        String gugun,
        String category,
        String keyword,
        Long pageNumber,
        Long limit) {
}
