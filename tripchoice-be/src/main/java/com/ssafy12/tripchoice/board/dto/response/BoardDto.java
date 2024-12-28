package com.ssafy12.tripchoice.board.dto.response;

import lombok.Builder;

@Builder
public record BoardDto(String boardId, // todo userName
                       String title,
                       String content,
                       String writer,
                       String createdAt,
                       String lastModifiedAt) {
}
