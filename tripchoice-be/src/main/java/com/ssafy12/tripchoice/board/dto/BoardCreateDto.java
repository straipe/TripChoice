package com.ssafy12.tripchoice.board.dto;

import lombok.Builder;

@Builder
public record BoardCreateDto(Long userId,
                             String title,
                             String content) {
}
