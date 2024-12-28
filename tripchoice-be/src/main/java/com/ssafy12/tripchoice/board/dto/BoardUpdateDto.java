package com.ssafy12.tripchoice.board.dto;

import lombok.Builder;

@Builder
public record BoardUpdateDto(Long userId,
                             Long boardId,
                             String title,
                             String content) {
}
