package com.ssafy12.tripchoice.tripplan.plan.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PlanSearchDto(
        @NotNull Integer page,
        @NotNull Integer size,
        @Nullable String title,
        @Nullable String author
) {
}
