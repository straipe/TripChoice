package com.ssafy12.tripchoice.tripplan.plan.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record PlanCreateDto(
        @NotNull Long userId,
        @NotEmpty String title,
        @NotEmpty String summary,
        String imgUrl,
        @NotNull List<ScheduleCreateDto> scheduleCreateDtos) {
}
