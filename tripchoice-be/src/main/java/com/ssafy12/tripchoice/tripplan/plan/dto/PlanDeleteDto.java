package com.ssafy12.tripchoice.tripplan.plan.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PlanDeleteDto(
        @NotNull Long userId,
        @NotNull Long planId) {
}
