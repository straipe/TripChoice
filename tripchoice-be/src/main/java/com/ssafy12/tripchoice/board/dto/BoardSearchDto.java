package com.ssafy12.tripchoice.board.dto;

import com.ssafy12.tripchoice.board.BoardSearchCondition;
import lombok.Builder;

@Builder
public record BoardSearchDto(Integer page,
                             Integer size,
                             BoardSearchCondition condition,
                             String keyword) {
}
