package com.ssafy12.tripchoice.common.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PageResponseDto(Integer currentPage,
                              Integer size,
                              Integer totalPage,
                              List<?> content) {
}
