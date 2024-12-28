package com.ssafy12.tripchoice.common.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PageRequestDto(@NotNull Integer page,
                             @NotNull Integer size) {
}
