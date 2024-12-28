package com.ssafy12.tripchoice.board.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record BoardUpdateRequestDto(@NotEmpty String title,
                                    @NotEmpty String content) {
}
