package com.ssafy12.tripchoice.tripplan.plan.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record ScheduleCreateDto(@NotNull List<Integer> attractionIds) {
}
