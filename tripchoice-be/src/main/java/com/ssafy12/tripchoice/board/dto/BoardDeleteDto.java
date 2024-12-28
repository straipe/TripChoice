package com.ssafy12.tripchoice.board.dto;

import lombok.Builder;

@Builder
public record BoardDeleteDto(Long userId, Long boardId) {
}
